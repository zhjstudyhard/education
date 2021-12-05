<!--
 * @Author: your name
 * @Date: 2021-11-21 19:30:08
 * @LastEditTime: 2021-12-03 20:36:52
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \vue-element-admin-master\src\views\permission\userList.vue
-->
<template>
  <div class="app-container">
    <el-input
      v-model="querySearch.username"
      placeholder="请输入用户名"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-input
      v-model="querySearch.realName"
      placeholder="请输入姓名"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-button type="success" icon="el-icon-search" @click="getUserList(1)"
      >搜索</el-button
    >
    <el-button type="warning" @click="resetData" icon="el-icon-refresh-left"
      >重置</el-button
    >
    <el-button type="primary" @click="handleAddUser" icon="el-icon-plus"
      >新增</el-button
    >

    <el-table :data="userList" style="width: 100%; margin-top: 30px" border>
      <el-table-column align="center" label="序号" width="50">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="真实姓名" width="100">
        <template slot-scope="scope">
          {{ scope.row.realName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名" width="100">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色" width="200">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="过期时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.expired }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否启用" width="50">
        <template slot-scope="scope">
          {{ scope.row.enabled == 0 ? "是" : "否" }}
        </template>
      </el-table-column>
      <!-- <el-table-column align="center" label="是否过期" width="50">
        <template slot-scope="scope">
          {{ scope.row.isExpired == 0 ? "否" : "是" }}
        </template>
      </el-table-column> -->
      <el-table-column align="center" label="是否锁定" width="50">
        <template slot-scope="scope">
          {{ scope.row.locked == 0 ? "否" : "是" }}
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="handleEdit(scope)"
            icon="el-icon-edit"
            >编辑</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="handleEditLocked(scope)"
            icon="el-icon-edit"
            v-if = "scope.row.locked == 0"
            >锁定</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="handleEditUnlock(scope)"
            icon="el-icon-edit"
            v-if = "scope.row.locked == 1"
            >解锁</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="handleEditDisable(scope)"
            icon="el-icon-edit"
            v-if = "scope.row.enabled == 0"
            >停用</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="handleEditEnable(scope)"
            icon="el-icon-edit"
            v-if = "scope.row.enabled == 1"
            >启用</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="handleDelete(scope.row.id)"
            icon="el-icon-delete"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :visible.sync="dialogVisible"
      :title="dialogType === 'edit' ? '修改用户' : '新增用户'"
      @close="closeDialog"
    >
      <el-form :model="user" label-width="80px" label-position="left">
        <el-row>
          <el-col :span="10">
            <el-form-item label="用户名" style="margin-right: 30px" :required="true">
              <el-input v-model="user.username" placeholder="用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="真实姓名" :required="true">
              <el-input v-model="user.realName" placeholder="真实姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色" style="margin-right: 30px" :required="true">
              <el-select
                v-model="user.roleId"
                placeholder="选择角色"
              >
                <el-option
                  v-for="role in roleList"
                  :key="role.id"
                  :label="role.roleName"
                  :value="role.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="状态" :required="true">
              <el-select v-model="user.enabled" placeholder="启用或禁用">
                <el-option label="启用" :value="0"></el-option>
                <el-option label="停用" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="过期时间">
              <el-date-picker
                v-model="user.expired"
                type="datetime"
                value-format = "yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div style="text-align: right">
        <el-button type="danger" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmUser">确认</el-button>
      </div>
    </el-dialog>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getUserList"
    />
  </div>
</template>

<script>
import user from "@/api/system/user";
import role from "@/api/system/role";
export default {
  data() {
    return {
      page: 1, // 当前页
      limit: 10, //每页显示数据量
      total: 0, //总数量
      userList: [],
      roleList: [],
      user: {
        id: "",
        username: "",
        realName: "",
        enabled: "",
        expired: "",
        roleId: "",
      },
      querySearch: {
        currentPage: 1,
        username: "",
        realName: "",
      },
      queryRoleSearch: {
        roleName: "",
      },
      // role: Object.assign({}, defaultRole),
      dialogVisible: false,
      dialogType: "new",
      checkStrictly: false,
    };
  },
  created() {
    //分页查询字典列表
    this.getUserList();
    this.getRoleList();
    // Mock: get all routes and roles list from serve
  },
  methods: {
    //分页查询所有用户
    getUserList(page = 1) {
      this.page = page;
      //传递参数
      this.querySearch.currentPage = this.page;
      user.queryUserAllPage(this.querySearch).then((response) => {
        this.userList = response.data.data.data;
        this.total = response.data.data.total;
      });
    },
    //查询所有角色
    getRoleList() {
      //传递参数
      role.queryRoleAll(this.queryRoleSearch).then((response) => {
        this.roleList = response.data.data;
      });
    },
    //重置数据
    resetData() {
      this.querySearch = {};
      this.getUserList(1);
    },

    //删除用户
    handleDelete(id) {
      let data = { id: id };
      this.$confirm("确认删除当前用户?", "Warning", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then((response) => {
          user.deleteUser(data).then((response) => {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            this.getUserList(1);
          });
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //   // Reshape the routes structure so that it looks the same as the sidebar
    //新增角色
    handleAddUser() {
      this.dialogType = "add";
      this.dialogVisible = true;
    },
    //编辑角色
    handleEdit(scope) {
      this.dialogType = "edit";
      this.dialogVisible = true;
      // this.checkStrictly = true;
      //赋值
      this.user = JSON.parse(JSON.stringify(scope.row));
    },
    async confirmUser() {
      const isEdit = this.dialogType === "edit";
      if (isEdit) {
        await user.editUser(this.user).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
        });
      } else {
        await user.addUser(this.user).then((response) => {
          this.$message({
            type: "success",
            message: "添加成功!",
          });
        });
      }
       this.dialogVisible = false;
       this.getUserList(1);
    },
    //对话框关闭事件
    closeDialog() {
      this.user = {};
    },
    //锁定
    handleEditLocked(scope){
       this.user = JSON.parse(JSON.stringify(scope.row));
       this.user.locked = 1
       user.editUser(this.user).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.user = {};
          this.getUserList(1);
        });
    },
     //解锁
    handleEditUnlock(scope){
       this.user = JSON.parse(JSON.stringify(scope.row));
       this.user.locked = 0
        user.editUser(this.user).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.user = {};
          this.getUserList(1);
        });
    },
    //启用
    handleEditEnable(scope){
        this.user = JSON.parse(JSON.stringify(scope.row));
        this.user.enabled = 0
        user.editUser(this.user).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          
          this.user = {};
          this.getUserList(1);
        });
    },
    //停用
    handleEditDisable(scope){
        this.user = JSON.parse(JSON.stringify(scope.row));
        this.user.enabled = 1
        user.editUser(this.user).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.user = {};
          this.getUserList(1);
        });
    }
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  .roles-table {
    margin-top: 30px;
  }
  .permission-tree {
    margin-bottom: 30px;
  }
}
</style>
