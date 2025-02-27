<template>
  <div>
    <!-- 场所码管理页面 -->
    <div class="module">
      <div class="tittle">新建场所码</div>
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules">
        <div class="form">
          <el-form-item label="" prop="identity_card">
            <el-input
              v-model.number="value"
              v-model="ruleForm.identity_card"
              size="small"
              oninput="value=value.replace(/[^0-9.]/g,'')"
              placeholder="请输入负责人身份证号"
            />
          </el-form-item>
          <el-form-item label="" prop="district_index">
            <el-select
              v-model="ruleForm.district_index"
              placeholder="请选择行政区"
              size="small"
              @change="changedistrict"
            >
              <el-option
                v-for="(item, i) in district"
                :key="item.code"
                :label="item.name"
                :value="i"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="community_index">
            <el-select
              v-model="ruleForm.community_index"
              placeholder="请选择社区"
              size="small"
            >
              <el-option
                v-for="(item, i) in community"
                :key="item.code"
                :label="item.name"
                :value="i"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="name">
            <el-input
              v-model="ruleForm.name"
              size="small"
              placeholder="请输入场所名"
            />
          </el-form-item>
          <el-form-item label="" prop="street_index">
            <el-select
              v-model="ruleForm.street_index"
              placeholder="请选择街道"
              size="small"
              @change="changestreet"
            >
              <el-option
                v-for="(item, i) in street"
                :key="item.code"
                :label="item.name"
                :value="i"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="address">
            <el-input
              v-model="ruleForm.address"
              size="small"
              placeholder="请输入地址"
            />
          </el-form-item>
        </div>
      </el-form>
      <el-button
        type="primary"
        size="small"
        @click="submit('ruleForm')"
      >添加</el-button>
    </div>
    <div class="module">
      <div class="tittle">场所码列表</div>
      <div class="table">
        <el-table
          v-loading="reverseloading"
          :data="tableData"
          style="width: 100%"
        >
          <el-table-column prop="pid" label="场所ID" width="200" align="center" />
          <el-table-column prop="placeName" label="场所名" width="120" align="center" />
          <el-table-column prop="identityCard" label="负责人身份证号" width="200" align="center" />
          <el-table-column prop="district" label="行政区" width="120" align="center">
            <template slot-scope="scope">
              {{ scope.row.district || scope.row.districtId }}
            </template>
          </el-table-column>
          <el-table-column prop="street" label="街道" width="120" align="center">
            <template slot-scope="scope">
              {{ scope.row.street || scope.row.streetId }}
            </template>
          </el-table-column>
          <el-table-column prop="community" label="社区" align="center">
            <template slot-scope="scope">
              {{ scope.row.community || scope.row.communityId }}
            </template> </el-table-column>
          <el-table-column prop="address" label="地址" align="center" />
          <el-table-column prop="address" label="关停/开启" width="120" align="center">
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
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import * as villages from '@/utils/villages'
export default {
  data() {
    return {
      reverseloading: '',
      value: '',
      rules: {
        identity_card: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { min: 18, max: 18, message: '请输入正确身份证号', trigger: 'blur' }
        ],
        name: [{ required: true, message: '请输入场所名', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
        district_index: [
          { required: true, message: '请选择', trigger: 'blur' }
        ],
        street_index: [{ required: true, message: '请选择', trigger: 'blur' }],
        community_index: [
          { required: true, message: '请选择', trigger: 'blur' }
        ]
      },
      district: [],
      street: [],
      community: [],
      ruleForm: {
        identity_card: '',
        name: '',
        district_id: '',
        street_id: '',
        community_id: '',
        district_index: '',
        street_index: '',
        community_index: '',
        address: ''
      },
      tableData: [{ pid: '54321', name: '样例', identity_card: '142602199711091345', district: '岳麓区', street: '麓山南路', community: '天马小区', address: '天马学生公寓' },
        { pid: '54321', name: '样例', identity_card: '142602199711091345', district: '岳麓区', street: '麓山南路', community: '天马小区', address: '天马学生公寓' }]
    }
  },
  created() {
    this.setSelect(0, 0, 0)
    this.getData()
  },
  methods: {
    /**
     * 设置选中的区域、街道和社区的索引，并更新相关数据
     * @param {number} district_index - 区域的索引
     * @param {number} street_index - 街道的索引
     * @param {number} community_index - 社区的索引
     */
    setSelect(district_index, street_index, community_index) {
      console.log(district_index, street_index, community_index)
      // 将传入的索引值赋值给表单数据中的对应字段
      this.ruleForm.district_index = district_index
      this.ruleForm.street_index = street_index
      this.ruleForm.community_index = community_index
      // 根据索引获取对应的区域数据
      this.district = villages.children
      // 根据区域索引获取对应的街道数据
      this.street = this.district[district_index].children
      // 根据街道索引获取对应的社区数据
      this.community = this.street[street_index].children
      // 将区域、街道、社区的 code 转换为数字并赋值给表单数据中的对应字段
      this.ruleForm.district_id = Number(this.district[district_index].code)
      this.ruleForm.street_id = Number(this.street[street_index].code)
      this.ruleForm.community_id = Number(this.community[community_index].code)
    },
    /**
     * 递归函数，用于查找匹配的 code
     * @param {Object} data - 要查找的数据源对象
     * @param {string | number} code - 要匹配的 code 值
     * @returns {Object | null} 找到匹配的对象则返回该对象，否则返回 null
     */
    findInfoByCode(data, code) {
      // 如果当前对象的 code 与传入的 code 相等，则返回当前对象
      if (data.code == code) {
        return data
      }
      // 如果当前对象有子节点且子节点数组长度大于 0，则递归遍历子节点
      if (data.children && data.children.length > 0) {
        for (let i = 0; i < data.children.length; i++) {
          const childResult = this.findInfoByCode(data.children[i], code)
          // 如果子节点中找到匹配的对象，则返回该对象
          if (childResult) {
            return childResult
          }
        }
      }
      // 未找到匹配的对象，返回 null
      return null
    },
    /**
     * 当区域选择发生变化时的处理方法，调用 setSelect 方法并传入当前区域索引和默认的街道、社区索引（0）
     */
    changedistrict() {
      this.setSelect(this.ruleForm.district_index, 0, 0)
    },
    /**
     * 当街道选择发生变化时的处理方法，调用 setSelect 方法并传入当前区域索引、当前街道索引和默认的社区索引（0）
     */
    changestreet() {
      this.setSelect(
        this.ruleForm.district_index,
        this.ruleForm.street_index,
        0
      )
    },
    /**
     * 提交信息，创建新的场所
     * @param {string} formName - 表单的引用名称
     */
    submit(formName) {
      console.log(this.ruleForm)
      // 调用表单的验证方法，传入验证回调函数
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 验证通过，发送 POST 请求创建新场所
          request({
            url: '/place-code/placeCode',
            method: 'post',
            data: {
              identity_card: this.ruleForm.identity_card,
              name: this.ruleForm.name,
              address: this.ruleForm.address,
              district_id: this.ruleForm.district_id,
              street_id: this.ruleForm.street_id,
              community_id: this.ruleForm.community_id
            }
          })
            .then((res) => {
              // 如果响应状态码为 200，说明创建成功，调用 getData 方法重新获取数据
              if (res.code === 200) this.getData()
            })
            .catch((res) => {
              console.log(res)
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    /**
     * 获取场所数据
     */
    getData() {
      this.reverseloading = true
      // 发送 GET 请求获取场所数据
      request({
        url: '/place-code/placeCode',
        method: 'get'
      })
        .then((res) => {
          this.reverseloading = false
          this.tableData = res.data
          // 遍历获取到的数据，根据 ID 查找对应的名称并更新数据
          this.tableData.forEach((item, index) => {
            this.tableData[index].district = this.findInfoByCode(villages, item.districtId).name
            this.tableData[index].street = this.findInfoByCode(villages, item.streetId).name
            this.tableData[index].community = this.findInfoByCode(villages, item.communityId).name
          })
          console.log(this.tableData)
        })
        .catch((res) => {
          this.reverseloading = false
        })
    },
    /**
     * 更改场所状态
     * @param {Object} row - 当前行的数据对象
     */
    changeData(row) {
      this.reverseloading = true
      // 发送 PATCH 请求更新场所状态
      request({
        url: '/place-code/place_code_opposite',
        method: 'patch',
        data: {
          pid: row.pid
          // status:Number(row.status)
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
  bottom: 20px;
  left: 780px;
}
</style>
