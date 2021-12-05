<template>
  <div class="app-container">
    <el-input
      v-model="name"
      placeholder="关键字输入"
      style="width: 250px; margin-right: 20px"
    />
    <!-- <el-button type="success" icon="el-icon-search" @click="fetchNodeList"
      >搜索</el-button
    > -->
    <el-button type="warning" @click="resetData" icon="el-icon-refresh-left"
      >重置</el-button
    >
    <el-table
      :data="treeTable"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
      default-expand-all
      :expand-row-keys="expandRow"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <!-- default-expand-all -->
      <el-table-column prop="name" label="名称" sortable width="180">
      </el-table-column>
      <el-table-column prop="path" label="访问路径" sortable width="180">
      </el-table-column>
      <el-table-column prop="component" label="组件路径" sortable width="180">
      </el-table-column>
      <el-table-column prop="permissionValue" label="权限值"> </el-table-column>
      <el-table-column
        prop="typeName"
        label="类型"
      >
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- v-if="node.level == 1 || node.level == 2" v-if="node.level == 3" v-if="node.level == 4"-->
          <el-button type="text" size="mini" @click="handleAddMenus(scope)"
            >添加菜单
          </el-button>
          <el-button type="text" size="mini" @click="() => getById(scope.row)"
            >修改菜单
          </el-button>
          <el-button type="text" size="mini" @click="handleAddPermission(scope)"
            >添加功能
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => updateFunction(scope.row)"
            >修改功能
          </el-button>

          <el-button type="text" size="mini" @click="() => remove(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="dialogFormVisible"
      :title="dialogFormValue === 'editMenus' ? '修改菜单' : '添加菜单'"
      @close="restData()"
    >
      <el-form
        ref="menu"
        :model="menu"
        :rules="menuValidateRules"
        label-width="120px"
      >
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="menu.name" />
        </el-form-item>
        <el-form-item label="访问路径" prop="path">
          <el-input v-model="menu.path" />
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-input v-model="menu.component" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="restData()">取 消</el-button>
        <el-button type="primary" @click="append()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 添加按钮权限的窗口 -->
    <el-dialog
      :visible.sync="dialogPermissionVisible"
      :title="
        dialogPermissionValue === 'editPermission' ? '修改功能' : '添加功能'
      "
      @close="restData()"
    >
      <el-form
        ref="permission"
        :model="permission"
        :rules="permissionValidateRules"
        label-width="120px"
      >
        <el-form-item label="功能名称" prop="name">
          <el-input v-model="permission.name" />
        </el-form-item>
        <el-form-item label="访问路径">
          <el-input v-model="permission.path" />
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="permission.component" />
        </el-form-item>
        <el-form-item label="功能权限值" prop="permissionValue">
          <el-input v-model="permission.permissionValue" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="restData()">取 消</el-button>
        <el-button type="primary" @click="appendPermission()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import menu from "@/api/system/menu";
const defaultMenuForm = {
  name: "",
  pid: 0,
  path: "",
  component: "",
  type: "1",
};

const defaultPerForm = {
  permissionValue: "",
  type: "2",
  name: "",
  path: "",
  component: "",
  pid: 0,
};

const menuForm = {
  name: "",
  pid: 0,
  path: "",
  component: "",
  type: "1",
};
const perForm = {
  permissionValue: "",
  type: "2",
  name: "",
  path: "",
  component: "",
  pid: 0,
};

export default {
  data() {
    return {
      name: "",
      menuList: [],
      expandRow: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
      dialogFormValue: "addMenus",
      dialogFormVisible: false,
      dialogPermissionValue: "addPermission",
      dialogPermissionVisible: false,
      menu: menuForm,
      permission: perForm,
      menuValidateRules: {
        name: [{ required: true, trigger: "blur", message: "菜单名必须输入" }],
        path: [
          { required: true, trigger: "blur", message: "菜单路径必须输入" },
        ],
        component: [
          { required: true, trigger: "blur", message: "组件名称必须输入" },
        ],
      },
      permissionValidateRules: {
        name: [
          { required: true, trigger: "blur", message: "功能名称必须输入" },
        ],
        permissionValue: [
          { required: true, trigger: "blur", message: "功能权限值必须输入" },
        ],
      },
    };
  },
  // 监听上面文本框搜索
  // watch: {
  //   filterText(val) {
  //     this.$refs.menuTree.filter(val);
  //   },
  // },
  computed: {
    treeTable: function () {
      var searchValue = this.name;
      if (searchValue) {
        let treeData = this.menuList;
        let handleTreeData = this.handleTreeData(treeData, searchValue);
        this.setExpandRow(handleTreeData);
        this.expandRow = this.expandRow.join(",").split(",");
        return handleTreeData;
      }
      return this.menuList;
    },
  },
  created() {
    this.fetchNodeList();
  },

  methods: {
    // 权限类别转换
    // formatPrivilegeType: function (row, column) {
    //   if (row.privilegeType === "1") {
    //     return "目录";
    //   } else if (row.privilegeType === "2") {
    //     return "菜单";
    //   } else if (row.privilegeType === "3") {
    //     return "按钮";
    //   } else {
    //     return "";
    //   }
    // },
    //  树形表格过滤
    handleTreeData(treeData, searchValue) {
      if (!treeData || treeData.length === 0) {
        return [];
      }
      const array = [];
      for (let i = 0; i < treeData.length; i += 1) {
        let match = false;
        for (let pro in treeData[i]) {
          if (typeof treeData[i][pro] == "string") {
            match |= treeData[i][pro].includes(searchValue);
            if (match) break;
          }
        }
        if (
          this.handleTreeData(treeData[i].children, searchValue).length > 0 ||
          match
        ) {
          array.push({
            ...treeData[i],
            children: this.handleTreeData(treeData[i].children, searchValue),
          });
        }
      }
      return array;
    },
    // 将过滤好的树形数据展开
    setExpandRow(handleTreeData) {
      if (handleTreeData.length) {
        for (let i of handleTreeData) {
          this.expandRow.push(i.id);
          if (i.children.length) {
            this.setExpandRow(i.children);
          }
        }
      }
    },

    fetchNodeList() {
      let data = { name: this.name };
      menu.getPermissionTreeList(data).then((response) => {
        this.menuList = response.data.children;
      });
    },
    // filterNode(value, data) {
    //   if (!value) return true;
    //   return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1;
    // },
    //添加菜单权限
    handleAddMenus(scope) {
      (this.dialogFormValue = "addMenus"),
        (this.dialogFormVisible = true),
        (this.menu.pid = scope.row.id);
    },
    //添加按钮权限
    handleAddPermission(scope) {
      (this.dialogPermissionValue = "addPermission"),
        (this.dialogPermissionVisible = true),
        (this.permission.pid = scope.row.id);
    },
    remove(data) {
      this.$confirm("确定删除当前权限?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return menu.remove(data);
        })
        .then(() => {
          this.fetchNodeList(); // 刷新列表
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch((response) => {
          // 失败
          if (response === "cancel") {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          } else {
            this.$message({
              type: "error",
              message: "删除失败",
            });
          }
        });
    },
    appendPermission() {
      this.$refs.permission.validate((valid) => {
        if (valid) {
          if (this.permission.id) {
            this.updateButton(this.permission);
          } else {
            menu.saveButtonLevelOne(this.permission).then((response) => {
              this.$message({
                type: "success",
                message: "添加功能成功",
              });
              // 刷新页面
              this.fetchNodeList();
              this.restData();
            });
          }
        }
      });
    },
    //新增一级分类菜单
    appendLevelOne() {
      menu.saveLevelOne(this.menu).then((response) => {
        this.dialogFormVisible = false;
        this.$message({
          type: "success",
          message: "添加一级菜单成功",
        });
        // 刷新页面
        this.fetchNodeList();
        this.restData();
      });
    },
    //更新菜单操作
    append() {
      this.$refs.menu.validate((valid) => {
        if (valid) {
          if (!this.menu.id) {
            // 添加
            if (this.menu.pid == 0) {
              this.appendLevelOne(); // 一级分类的添加
            } else {
              this.appendLevelTwo(); // 二级分类的添加
            }
          } else {
            // 修改
            this.update(this.menu);
          }
        }
      });
    },
    //更新菜单
    update(obj) {
      menu.update(obj).then((response) => {
        this.dialogFormVisible = false;
        this.$message({
          type: "success",
          message: "修改成功",
        });
        // 刷新页面
        this.fetchNodeList();
        this.restData();
      });
    },
    updateButton(obj) {
      menu.updateButton(obj).then((response) => {
        this.dialogPermissionVisible = false;
        this.$message({
          type: "success",
          message: "修改成功",
        });
        // 刷新页面
        this.fetchNodeList();
        this.restData();
      });
    },

    //新增二级分类菜单
    appendLevelTwo() {
      menu.saveLevelOne(this.menu).then((response) => {
        // 1、把文本框关
        this.dialogFormVisible = false;
        // 2、提示成功
        this.$message({
          type: "success",
          message: "添加二级分类成功",
        });
        // 3、刷新页面
        this.fetchNodeList();
        this.restData();
      });
    },
    //编辑菜单
    getById(data) {
      this.dialogFormVisible = true;
      (this.dialogFormValue = "editMenus"),
        (this.menu = JSON.parse(JSON.stringify(data)));
    },
    updateFunction(data) {
      (this.dialogPermissionValue = "editPermission"),
        (this.dialogPermissionVisible = true);
      this.permission = JSON.parse(JSON.stringify(data));
    },
    restData() {
      this.dialogPermissionVisible = false;
      this.dialogFormVisible = false;
      this.menu = {
        name: "",
        pid: 0,
        path: "",
        component: "",
        type: "1",
      };
      this.permission = {
        permissionValue: "",
        type: "2",
        name: "",
        path: "",
        component: "",
        pid: 0,
      };
      // this.menu = { ...menuForm };
      // this.permission = { ...perForm };
    },
    resetData() {
      this.name = "";
    },
  },
};
</script>
