<!--
 * @Author: your name
 * @Date: 2021-11-21 19:30:08
 * @LastEditTime: 2021-12-03 13:39:50
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \vue-element-admin-master\src\views\permission\userList.vue
-->
<template>
  <div class="app-container">
    <el-input
      v-model="querySearch.roleCode"
      placeholder="请输入角色编码"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-input
      v-model="querySearch.roleName"
      placeholder="请输入角色名称"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-button type="success" icon="el-icon-search" @click="getRoleList(1)"
      >搜索</el-button
    >
    <el-button type="warning" @click="resetData" icon="el-icon-refresh-left"
      >重置</el-button
    >
    <el-button type="primary" @click="handleAddRole" icon="el-icon-plus"
      >新增</el-button
    >

    <el-table :data="roleList" style="width: 100%; margin-top: 30px" border>
      <el-table-column align="center" label="序号" width="50">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色编码" width="250">
        <template slot-scope="scope">
          {{ scope.row.roleCode }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色名称" width="250">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" width="250">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate }}
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
            type="danger"
            size="small"
            @click="handleDelete(scope.row.id)"
            icon="el-icon-delete"
            >删除</el-button
          >
          <router-link :to="'/permission/role/distribution/' + scope.row.id">
            <el-button type="warning" size="small" icon="el-icon-setting"
              >分配权限</el-button
            >
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="dialogVisible"
      :title="dialogType === 'edit' ? '修改角色' : '新增角色'"
      @close="closeDialog"
    >
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item label="角色编码" :required="true">
          <el-input v-model="role.roleCode" placeholder="角色编码" />
        </el-form-item>
        <el-form-item label="角色名称" :required="true">
          <el-input v-model="role.roleName" placeholder="角色名称" />
        </el-form-item>
      </el-form>
      <div style="text-align: right">
        <el-button type="danger" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRole">确认</el-button>
      </div>
    </el-dialog>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getRoleList"
    />
  </div>
</template>

<script>
import role from "@/api/system/role";
export default {
  data() {
    return {
      page: 1, // 当前页
      limit: 10, //每页显示数据量
      total: 0, //总数量
      roleList: [],
      role: {
        id: "",
        roleCode: "",
        roleName: "",
      },
      querySearch: {
        currentPage: 1,
        roleCode: "",
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
    this.getRoleList();
    // Mock: get all routes and roles list from serve
  },
  methods: {
    //分页查询所有字典
    getRoleList(page = 1) {
      this.page = page;
      //传递参数
      this.querySearch.currentPage = this.page;
      role.queryRoleAllPage(this.querySearch).then((response) => {
        this.roleList = response.data.data.data;
        this.total = response.data.data.total;
      });
    },
    //重置数据
    resetData() {
      this.querySearch = {};
      this.getRoleList(1);
    },

    //删除数据字典
    handleDelete(id) {
      let data = { id: id };
      this.$confirm("确认删除当前角色?", "Warning", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then((response) => {
          role.deleteRole(data).then((response) => {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            this.getRoleList(1);
          });
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //   // Reshape the routes structure so that it looks the same as the sidebar
    //新增角色
    handleAddRole() {
      this.dialogType = "add";
      this.dialogVisible = true;
    },
    //编辑角色
    handleEdit(scope) {
      this.dialogType = "edit";
      this.dialogVisible = true;
      // this.checkStrictly = true;
      //赋值
      this.role = JSON.parse(JSON.stringify(scope.row));
    },
    async confirmRole() {
      const isEdit = this.dialogType === "edit";
      if (isEdit) {
        await role.editRole(this.role).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.getRoleList(1);
        });
      } else {
        await role.addRole(this.role).then((response) => {
          this.$message({
            type: "success",
            message: "添加成功!",
          });
          this.getRoleList(1);
        });
      }
      this.dialogVisible = false;
    },
    //对话框关闭事件
    closeDialog() {
      this.role = {};
    },
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
