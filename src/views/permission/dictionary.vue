<!--
 * @Author: your name
 * @Date: 2021-11-21 19:30:08
 * @LastEditTime: 2021-12-03 13:37:29
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \vue-element-admin-master\src\views\permission\userList.vue
-->
<template>
  <div class="app-container">
    <el-input
      v-model="querySearch.dictionaryCode"
      placeholder="请输入字典编码"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-input
      v-model="querySearch.dictionaryValue"
      placeholder="请输入字典内容"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-input
      v-model="querySearch.dictionaryType"
      placeholder="请输入字典类型"
      prefix-icon="el-icon-search"
      style="width: 250px; margin-right: 20px"
    ></el-input>
    <el-button
      type="success"
      icon="el-icon-search"
      @click="getDictionaryList(1)"
      >搜索</el-button
    >
    <el-button type="warning" @click="resetData" icon="el-icon-refresh-left"
      >重置</el-button
    >
    <el-button type="primary" @click="handleAddDictionary" icon="el-icon-plus"
      >新增</el-button
    >

    <el-table
      :data="dictionaryList"
      style="width: 100%; margin-top: 30px"
      border
    >
      <el-table-column align="center" label="序号" width="50">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="字典编码" width="250">
        <template slot-scope="scope">
          {{ scope.row.dictionaryCode }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="字典内容" width="250">
        <template slot-scope="scope">
          {{ scope.row.dictionaryValue }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="字典类型" width="250">
        <template slot-scope="scope">
          {{ scope.row.dictionaryType }}
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
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="dialogVisible"
      :title="dialogType === 'edit' ? '修改字典' : '新增字典'"
      @close="closeDialog"
    >
      <el-form
        ref="dictionary"
        :model="dictionary"
        :rules="dictionaryValidateRules"
        label-width="80px"
        label-position="left"
      >
        <el-form-item label="字典编码" :required="true">
          <el-input
            v-model="dictionary.dictionaryCode"
            placeholder="字典编码"
          />
        </el-form-item>
        <el-form-item label="字典内容" :required="true">
          <el-input
            v-model="dictionary.dictionaryValue"
            placeholder="字典内容"
          />
        </el-form-item>
        <el-form-item label="字典类型" :required="true">
          <el-input
            v-model="dictionary.dictionaryType"
            placeholder="字典类型"
          />
        </el-form-item>
      </el-form>
      <div style="text-align: right">
        <el-button type="danger" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmDictionary">确认</el-button>
      </div>
    </el-dialog>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getDictionaryList"
    />
  </div>
</template>

<script>
import dictionary from "@/api/system/dictionary";
export default {
  data() {
    return {
      page: 1, // 当前页
      limit: 10, //每页显示数据量
      total: 0, //总数量
      dictionaryList: [],
      input: "",
      dictionary: {
        id: "",
        dictionaryCode: "",
        dictionaryValue: "",
        dictionaryType: "",
      },
      querySearch: {
        currentPage: 1,
        dictionaryCode: "",
        dictionaryValue: "",
        dictionaryType: "",
      },
      // role: Object.assign({}, defaultRole),
      dialogVisible: false,
      dialogType: "new",
      checkStrictly: false,
      dictionaryValidateRules: {
        dictionaryCode: [
          { required: true, trigger: "blur", message: "字典编码不能为空" },
        ],
        dictionaryValue: [
          { required: true, trigger: "blur", message: "字典内容不能为空" },
        ],
        dictionaryType: [
          { required: true, trigger: "blur", message: "字典类型不能为空" },
        ],
      },
    };
  },
  created() {
    //分页查询字典列表
    this.getDictionaryList();
    // Mock: get all routes and roles list from serve
  },
  methods: {
    //分页查询所有字典
    getDictionaryList(page = 1) {
      this.page = page;
      //传递参数
      this.querySearch.currentPage = this.page;
      dictionary.queryDictionaryPage(this.querySearch).then((response) => {
        this.dictionaryList = response.data.data.data;
        this.total = response.data.data.total;
      });
    },
    //重置数据
    resetData() {
      this.querySearch = {};
      this.getDictionaryList(1);
    },
    //删除数据字典
    handleDelete(id) {
      let data = { id: id };
      this.$confirm("确认删除当前数据字典?", "Warning", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then((response) => {
          dictionary.deleteDictionary(data).then((response) => {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            this.getDictionaryList(1);
          });
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // Reshape the routes structure so that it looks the same as the sidebar
    //新增字典
    handleAddDictionary() {
      this.dialogType = "add";
      this.dialogVisible = true;
    },
    handleEdit(scope) {
      this.dialogType = "edit";
      this.dialogVisible = true;
      // this.checkStrictly = true;
      //赋值
      this.dictionary = JSON.parse(JSON.stringify(scope.row));
    },
    async confirmDictionary() {
      const isEdit = this.dialogType === "edit";
      if (isEdit) {
        await dictionary.editDictionary(this.dictionary).then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.getDictionaryList(1);
        });
      } else {
        await dictionary.addDictionary(this.dictionary).then((response) => {
          this.$message({
            type: "success",
            message: "添加成功!",
          });
          this.getDictionaryList(1);
        });
      }
      this.dialogVisible = false;
    },
    //对话框关闭事件
    closeDialog() {
      this.dictionary = {};
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
