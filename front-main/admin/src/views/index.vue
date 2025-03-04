<template>
  <div>
    <!-- 首页页面 -->
    <Card title="核酸检测统计">
      <div class="checkbox">
        <span>请选择开始时间</span>
        <el-date-picker
          v-model="detectTime.start"
          size="small"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        />
        <span>请选择截止时间</span>

        <el-date-picker
          v-model="detectTime.end"
          size="small"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        />
        <el-button
          type="primary"
          size="small"
          @click="getDetect()"
        >查询</el-button>
      </div>

      <div class="board">
        <el-container v-loading="detectloading">
          <div class="item">
            <div class="num">{{ detect.record }}</div>
            <div class="text">核酸检测参与记录数</div>
          </div>
          <div class="item">
            <div class="num">{{ detect.uncheck }}</div>
            <div class="text">未出结果记录数</div>
          </div>
          <div class="item">
            <div class="num">{{ detect.onePositive }}</div>
            <div class="text">单管阳性结果数</div>
          </div>
          <div class="item">
            <div class="num">{{ detect.positive }}</div>
            <div class="text">混管阳性结果数</div>
          </div>
        </el-container>
      </div>
    </Card>

    <Card title="阳性人员信息统计">
      <div class="checkbox">
        <span>请选择开始时间</span>
        <el-date-picker
          v-model="positiveTime.start"
          size="small"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        />
        <span>请选择截止时间</span>

        <el-date-picker
          v-model="positiveTime.end"
          size="small"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        />
        <el-button type="primary" size="small" @click="getPositive">查询</el-button>
      </div>
      <el-table v-loading="positiveloading" :data="tableData" style="width: 100%">
        <el-table-column prop="tubeid" label="试管ID" width="80" align="center" />
        <el-table-column label="试管种类" align="center">
          <template slot-scope="scope">
            {{ tubeType[scope.row.kind] }}
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="80" align="center" />
        <el-table-column prop="phoneNumber" label="手机" width="120" align="center" />
        <el-table-column prop="identityCard" label="身份证号" width="180" align="center" />
        <el-table-column
          prop="district"
          label="行政区"
          width="80"
          align="center"
        />
        <el-table-column
          prop="street"
          label="街道"
          width="80"
          align="center"
        />
        <el-table-column prop="community" label="社区" width="80" align="center" />
        <el-table-column prop="address" label="地址" align="center" />
        <el-table-column
          prop="testAddress"
          label="检测地址"
          width="120"
          align="center"
        />
        <el-table-column prop="testingOrganization" label="检测机构" width="120" align="center" />
        <el-table-column prop="createdAt" label="检测时间" width="120" align="center" />
        <el-table-column label="检测结果" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.result == 0" type="success">阴性</el-tag>
            <el-tag v-if="scope.row.result == 1" type="danger">阳性</el-tag>
            <el-tag v-if="scope.row.result == 2" type="info">未出</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </Card>
  </div>
</template>

<script>
import request from '@/utils/request'
import * as villages from '@/utils/villages'

export default {
  data() {
    return {
      detectloading: false,
      positiveloading: false,
      detect: {},
      value1: '',
      detectTime: {
        start: '',
        end: ''
      },
      positiveTime: {
        start: '',
        end: ''
      },
      tableData: [],
      tubeType: ['单管', '十人混管', '二十人混管']
    }
  },
  created() {
    this.getDetect()
    this.getPositive()
  },
  methods: {
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
     * 核酸检测统计接口
     */
    getDetect() {
      // console.log(this.detectTime);
      this.detectloading = true
      request({
        url: '/nucleic-acids/getNucleicAcidTestInfo',
        method: 'get',
        params: {
          start_time: this.detectTime.start,
          end_time: this.detectTime.end
        }
      }).then((res) => {
        this.detectloading = false
        this.detect = res.data
      }).catch(() => {
        this.detectloading = false
      })
    },
    /**
     * 获取阳性人员信息接口
     */
    getPositive() {
      // console.log(this.detectTime);
      this.positiveloading = true
      request({
        url: '/nucleic-acids/getPositiveInfo',
        method: 'get',
        params: {
          start_time: this.positiveTime.start,
          end_time: this.positiveTime.end
        }
      }).then((res) => {
        this.positiveloading = false
        this.tableData = res.data
        this.tableData.forEach((item, index) => {
          this.tableData[index].district = this.findInfoByCode(villages, item.district).name
          this.tableData[index].street = this.findInfoByCode(villages, item.street).name
          this.tableData[index].community = this.findInfoByCode(villages, item.community).name
        })
      }).catch(() => {
        this.positiveloading = false
      })
    }
  }
}
</script>

<style scoped>
.checkbox {
  margin: 20px 0;
}
.board {
  background-color: #f7faff;
  padding: 24px 0px;
  margin-top: 16px;
  display: flex;
  justify-content: center;
}
.board .item {
  flex: 1;
  text-align: center;
}
.board .item .num {
  opacity: 1;

  /** 文本1 */
  font-size: 14px;
  font-weight: 700;
  line-height: 23.44px;
  color: rgba(32, 32, 32, 1);
}
.board .item .text {
  opacity: 0.6;
  /** 文本1 */
  font-size: 12px;
  font-weight: 400;
  line-height: 16.41px;
  color: rgba(32, 32, 32, 1);
}
.checkbox span {
  color: #202020;
  opacity: 0.6;
  font-size: 12px;
  font-weight: 400;
  margin-right: 21px;
}
* >>> .el-date-editor.el-input,
.el-date-editor.el-input__inner {
  margin-right: 50px;
}
</style>
