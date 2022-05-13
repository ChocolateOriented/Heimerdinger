<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发生时间" prop="occurredTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.occurredTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择发生时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="数值" prop="ammount">
        <el-input
          v-model="queryParams.ammount"
          placeholder="请输入数值"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="较上期变化率" prop="changeRate">
        <el-input
          v-model="queryParams.changeRate"
          placeholder="请输入较上期变化率"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件类型" prop="eventType">
        <el-select v-model="queryParams.eventType" placeholder="请选择事件类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.finance_event_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="事件名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入事件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['finance:financeEventTrace:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:financeEventTrace:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['finance:financeEventTrace:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['finance:financeEventTrace:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="financeEventTraceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="发生时间" align="center" prop="occurredTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.occurredTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数值" align="center" prop="ammount" />
      <el-table-column label="较上期变化率" align="center" prop="changeRate" />
      <el-table-column label="事件类型" align="center" prop="eventType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.finance_event_type" :value="scope.row.eventType"/>
        </template>
      </el-table-column>
      <el-table-column label="事件名称" align="center" prop="name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:financeEventTrace:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:financeEventTrace:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改经济事件追踪对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="事件类型" prop="eventType">
          <el-select v-model="form.eventType" placeholder="请选择事件类型">
            <el-option
              v-for="dict in dict.type.finance_event_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="事件名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入事件名称" />
        </el-form-item>

        <el-form-item label="发生时间" prop="occurredTime">
          <el-date-picker clearable size="small"
            v-model="form.occurredTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发生时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="数值" prop="ammount">
          <el-input v-model="form.ammount" placeholder="请输入数值" v-on:change="getChangeRate" />
        </el-form-item>

        <el-form-item label="较上期变化率" prop="changeRate">
          <el-input v-model="form.changeRate" placeholder="请输入较上期变化率" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFinanceEventTrace, getFinanceEventTrace, delFinanceEventTrace, addFinanceEventTrace, updateFinanceEventTrace, exportFinanceEventTrace } from "@/api/finance/financeEventTrace";

export default {
  name: "FinanceEventTrace",
  dicts: ['finance_event_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 经济事件追踪表格数据
      financeEventTraceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        occurredTime: null,
        ammount: null,
        changeRate: null,
        eventType: null,
        name: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询经济事件追踪列表 */
    getList() {
      this.loading = true;
      listFinanceEventTrace(this.queryParams).then(response => {
        this.financeEventTraceList = response.rows;
        this.total = response.total;
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
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        occurredTime: null,
        ammount: null,
        changeRate: null,
        eventType: null,
        name: null
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
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加经济事件追踪";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFinanceEventTrace(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改经济事件追踪";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFinanceEventTrace(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFinanceEventTrace(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除经济事件追踪编号为"' + ids + '"的数据项？').then(function() {
        return delFinanceEventTrace(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有经济事件追踪数据项？').then(() => {
        this.exportLoading = true;
        return exportFinanceEventTrace(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
    getChangeRate(){
      this.form.changeRate;
      this.form.eventType;
      this.form.occurredTime;
      listFinanceEventTrace({"params":{
          "order":"occurred_time desc",
          "endTime":this.form.occurredTime,
        },
        "eventType":this.form.eventType
      }).then(response => {
        if (!response.rows || response.rows.length <= 0){
          return;
        }
        response.rows[0]
        console.info(response);
      });
    }
  }
};
</script>
