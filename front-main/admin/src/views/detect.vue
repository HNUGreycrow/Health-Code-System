<template>
  <div>
    <!-- 核酸检测人员管理页面 -->
    <div class="module">
      <div class="tittle">新建核酸检测人员</div>
      <el-form ref="ruleForm" v-loading="addloading" :model="ruleForm" :rules="rules">
        <div class="form">
          <el-form-item label="" prop="name">
            <el-input
              v-model="ruleForm.name"
              placeholder="请输入姓名"
              size="small"
            />
          </el-form-item>
          <el-form-item label="" prop="identity_card">
            <el-input
              v-model.number="value"
              v-model="ruleForm.identity_card"
              size="small"
              oninput="value=value.replace(/[^0-9.]/g,'')"
              placeholder="请输入身份证号"
            />
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input
              v-model="ruleForm.password"
              placeholder="请输入密码"
              size="small"
            />
          </el-form-item>
        </div>
      </el-form>

      <el-button type="primary" size="small" @click="submit('ruleForm')">添加</el-button>
    </div>
    <div class="module">
      <div class="tittle">核酸检测人员列表</div>
      <div v-loading="reverseloading" class="table">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="tid" label="人员ID" />
          <el-table-column prop="name" label="姓名" />
          <el-table-column
            prop="identityCard"
            label="身份证号"
            align="center"
          />

          <el-table-column prop="name" label="密码" align="center">
            XXXXXXXXXXXXXXXXXXX
          </el-table-column>
          <el-table-column prop="address" label="关停/开启" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-color="#47C93A"
                inactive-color="#FF4957"
                @click.native.prevent="changeData(scope.row)"
              />
            </template>
          </el-table-column>
        </el-table>
        <!-- <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        background
        :current-page="currentPage4"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="10000"
      >
      </el-pagination> -->
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  data() {
    return {
      reverseloading: false,
      addloading: false,
      ruleForm: {
        password: '',
        identity_card: '',
        name: ''
      },
      rules: {
        identity_card: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { min: 18, max: 18, message: '请输入正确身份证号', trigger: 'blur' }
        ],
        name: [{ required: true, message: '请输姓名', trigger: 'blur' }],
        password: [{ required: true, message: '密码', trigger: 'blur' }]

      },
      tableData: [
      ]
    }
  },
  created() {
    this.getData()
  },
  methods: {
    /**
     * 提交新增的核酸检测人员表单
     * @param formName 表单名称
     */
    submit(formName) {
      console.log(this.ruleForm)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            url: '/user/nucleic_acid',
            method: 'post',
            data: {
              ...this.ruleForm
            }
          }).then((res) => {
            this.addloading = false
            if (res.code === 200) {
              this.$message({
                message: '添加成功',
                type: 'success'
              })
              this.getData()
            }
          })
            .catch((res) => {
              this.addloading = false
              console.log(res)
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    /**
     * 获取核酸检测人员数据
     */
    getData() {
      this.reverseloading = true
      request({
        url: '/user/nucleic_acid',
        method: 'get'
      })
        .then((res) => {
          console.log(res)
          this.reverseloading = false
          this.tableData = res.data
        })
        .catch((res) => {
          console.log(res)
          this.reverseloading = false
        })
    },
    /**
     * 对核酸检测人员进行开启/关停
     * @param row 当前行的数据对象
     */
    changeData(row) {
      this.reverseloading = true
      request({
        url: '/user/nucleic_acid_opposite',
        method: 'patch',
        data: {
          tid: row.tid
        }
      })
        .then((res) => {
          this.reverseloading = false
          this.getData()
        })
        .catch((res) => {
          this.reverseloading = false
          this.getData()
        })
    }
  }
}
</script>

<style scoped>
.table {
  margin: 20px 0;
}
.form {
  display: flex;
  justify-content: left;
  width: 900px;
  flex-wrap: wrap;

  /* float: left; */
}
.form >>> .el-input {
  width: 200px;
  margin-right: 50px;
  margin-top: 20px;
}
* >>> .el-button {
  position: absolute;
  bottom: 25px;
  left: 780px;
}
</style>
