<template>
  <div class="app-container">
    <el-main style="flex-grow: 1; overflow-y: auto; height: 90vh;">
      <el-row>
        <el-col :span="5">
          <div style="flex-grow: 1; overflow: scroll; height: 85vh;">
            <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
              <el-form-item label="关键字" prop="keyword">
                <el-input
                  v-model="queryParams.keyword"
                  placeholder="请输入关键字"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              </el-form-item>
            </el-form>

            <el-table v-loading="loading" :data="stockSubjectList" highlight-current-row @current-change="handleCurrentChange">
              <el-table-column label="名称" align="center" prop="name"/>
              <el-table-column label="代码" align="center" prop="code"/>
              <el-table-column label="评分" align="center" prop="score"/>
            </el-table>
          </div>
        </el-col>
        <el-col :span="19">
          <el-tabs :tab-position="tabPosition" style="height: 85vh;">
<!--            循环-->
            <el-tab-pane v-for="item in stockReportList" :label="item.report_DATE" :name="item.code">
              <div style=" overflow: scroll">
<!--                让文字更美观, 调整行高等属性-->
                <div style="line-height: 2em;" v-html="item.business_REVIEW"></div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script>
import {listStockSubject, listStockReport} from "@/api/finance/stockSubject";
export default {
  name: "StockDict",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportReport: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      tabPosition: 'left',
      // 总条数
      total: 0,
      // 股票字典表格数据
      stockSubjectList: [],
      // 股票报告列表
      stockReportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        keyword: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    // this.getList();
  },
  methods: {
    /** 查询股票字典列表 */
    getList() {
      this.loading = true;
      listStockSubject(this.queryParams).then(response => {
        this.stockSubjectList = response.data;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        code: null,
        bourse: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleCurrentChange(selection) {
      listStockReport({'code':selection.code}).then(response => {
        this.stockReportList = response.data;        // 标红business_REVIEW中的关键字
        this.stockReportList.forEach(item => {
          item.business_REVIEW = item.business_REVIEW.replace(new RegExp(this.queryParams.keyword, 'g'), '<em style="color: #f56c6c;  font-weight: bold;">' + this.queryParams.keyword + '</em>');
        });
        this.loading = false;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加股票字典";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStockDict(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改股票字典";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStockDict(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStockDict(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除股票字典编号为"' + ids + '"的数据项？').then(function () {
        return delStockDict(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有股票字典数据项？').then(() => {
        this.exportLoading = true;
        return exportStockDict(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
