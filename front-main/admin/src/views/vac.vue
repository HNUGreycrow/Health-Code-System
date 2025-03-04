<template>
  <Card title="疫苗接种记录列表">
    <div class="checkbox">
      <el-input v-model="input" placeholder="请输入身份证号" size="small" />
      <el-button type="primary" size="small" @click="search"> 查询</el-button>
    </div>

    <div v-loading="reverseloading" class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="uid" label="用户id" align="center" />
        <el-table-column prop="vaccDate" label="接种时间" align="center" />
        <el-table-column prop="vaccineType" label="疫苗种类" align="center" />
        <el-table-column prop="name" label="接种地点" align="center" />
        <el-table-column prop="vaccDate" label="接种日期" align="center" />
      </el-table>
    </div>
  </Card>
</template>

<script>
import request from '@/utils/request'
export default {
  data() {
    return {
      input: '',
      reverseloading: false,
      tableData: []
      // tableData: [{ time: '2023-11-02', kind: '样例疫苗', address: '湖南省人民医院' },
      //   { time: '2023-06-02', kind: '科兴疫苗', address: '湖南省人民医院' }]
    }
  },
  created() {
    this.search()
  },
  methods: {
    changeData(row) {

    },
    // 查询疫苗接种记录
    search() {
      request({
        url: '/vaccine-inoculation/vaccinationRecord',
        method: 'get',
        params: {
          idCard: this.input
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    }
  }
}
</script>

<style scoped>
.checkbox {
  margin: 20px 0;
  display: flex;
}

.checkbox >>> .el-input {
  width: 300px;
  margin-right: 50px;
}
</style>
