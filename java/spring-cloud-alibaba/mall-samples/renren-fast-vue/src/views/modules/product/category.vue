<template>
  <div>
    <el-tree
      :data='menus'
      :props='defaultProps'
      show-checkbox
      node-key='catId'
      :expand-on-click-node='false'
      :default-expanded-keys='expandedKey'
      :draggable="true"
      :allow-drop="true"
    >
    <span class='custom-tree-node' slot-scope='{ node, data }'>
      <span>{{ node.label }}</span>
      <span>
        <el-button v-if='node.level <=2' type='text' size='mini' @click='() => append(data)'> 添加</el-button>
        <el-button type="text" size="mini" @click="edit(data)">| 编辑</el-button>
        <el-button v-if='node.childNodes.length===0' type='text' size='mini'
                   @click='() => remove(node, data)'>| 删除 </el-button>
      </span>
    </span>
    </el-tree>

    <el-dialog
      title='提示'
      :visible.sync='dialogVisible'
      :close-on-click-modal = "false"
      width='30%'>
      <el-form :model="category">
        <el-form-item label="分类名称">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="计量单位">
          <el-input v-model="category.productUnit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitData">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
let id = 1000;

export default {
  data() {
    return {
      dialogType: "",
      category: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        productUnit: "",
        icon: "",
        catId: null
      },
      dialogVisible: false,
      menus: [],
      expandedKey: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },

  methods: {
    // 获取数据列表
    getCategoryList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/category/list/tree'),
        method: 'get'
      }).then(({data}) => {
        this.menus = data.data
        this.dataListLoading = false
      })
    },
    edit(data) {
      this.dialogType = "edit";
      this.title = "修改分类";
      this.dialogVisible = true;

      //发送请求获取当前节点最新的数据
      this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: "get"
      }).then(({data}) => {
        //请求成功
        this.category.name = data.data.name;
        this.category.catId = data.data.catId;
        this.category.icon = data.data.icon;
        this.category.productUnit = data.data.productUnit;
        this.category.parentCid = data.data.parentCid;
        this.category.catLevel = data.data.catLevel;
        this.category.sort = data.data.sort;
        this.category.showStatus = data.data.showStatus;
      });
    },
    append(data) {
      this.dialogVisible = true
      this.title = "添加分类";
      this.dialogType = "add";
      this.category.parentCid = data.catId;
      this.category.catLevel = data.catLevel + 1;
      this.category.catId = null;
      this.category.name = "";
      this.category.icon = "";
      this.category.productUnit = "";
      this.category.sort = 0;
      this.category.showStatus = 1;

    },
    submitData() {
      if (this.dialogType === "add") {
        this.addCategory();
      }
      if (this.dialogType === "edit") {
        this.editCategory();
      }
    },
    //添加三级分类
    addCategory() {
      console.log("提交的三级分类数据", this.category);
      this.$http({
        url: this.$http.adornUrl("/product/category/save"),
        method: "post",
        data: this.$http.adornData(this.category, false)
      }).then(({data}) => {
        this.$message({
          message: "菜单保存成功",
          type: "success"
        });
        //关闭对话框
        this.dialogVisible = false;
        //刷新出新的菜单
        this.getMenus();
        //设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      });
    },
    //修改三级分类数据
    editCategory() {
      var {catId, name, icon, productUnit} = this.category;
      this.$http({
        url: this.$http.adornUrl("/product/category/update"),
        method: "post",
        data: this.$http.adornData({catId, name, icon, productUnit}, false)
      }).then(({data}) => {
        this.$message({
          message: "菜单修改成功",
          type: "success"
        });
        //关闭对话框
        this.dialogVisible = false;
        //刷新出新的菜单
        this.getMenus();
        //设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      });
    },
    remove(node, data) {
      let ids = [data.catId]
      this.$confirm(`确定对[${data.name}]进行[删除]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl(`/product/category/delete`),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          this.$message({
            message: '菜单删除成功',
            type: 'success'
          })
          // 刷新出新的菜单
          this.getCategoryList()
          // 设置需要默认展开的菜单
          this.expandedKey = [node.parent.data.catId]
        })
      }).catch(() => {
      })
    }
  },
  created() {
    this.getCategoryList()
  }
}
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px
}
</style>
