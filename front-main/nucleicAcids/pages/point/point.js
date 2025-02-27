const app = getApp()
const villages = require('../../utils/villages');  // 引入villages数据

Page({
  data: {
    name: '',
    address: '',
    time: '',
    contact: '',
    area: {
      arr: [], // 区域选择项
      obj: [], // 区域对应的对象
      index: 0 // 默认选择
    },
    street: {
      arr: [], // 街道选择项
      obj: [],
      index: 0
    },
    village: {
      arr: [], // 社区选择项
      obj: [],
      index: 0
    },
  },

  onLoad() {
    const temp = require('../../utils/villages')
    this.setData({
      temp
    });
    this.getpca(0, 0, 0);  // 默认初始化区域、街道、社区选择
  },

  bindKeyInput(e) {
    const index = e.target.dataset.index;
    const value = e.detail.value;
    if (index === "name") {
      this.setData({ name: value });
    } else if (index === "address") {
      this.setData({ address: value });
    } else if (index === "time") {
      this.setData({ time: value });
    } else if (index === "contact") {
      this.setData({ contact: value });
    } else if (index === "area") {
      this.getpca(e.detail.value, 0, 0);
    } else if (index === "street") {
      this.getpca(this.data.area.index, e.detail.value, 0);
    } else if (index === "village") {
      this.getpca(this.data.area.index, this.data.street.index, e.detail.value);
    }
  },

  getpca(areaIndex, streetIndex, villageIndex) {
    this.setData({
      area: { ...this.getArrObj(this.data.temp.children), index: areaIndex },
      street: { ...this.getArrObj(this.data.temp.children[areaIndex].children), index: streetIndex },
      village: { ...this.getArrObj(this.data.temp.children[areaIndex].children[streetIndex].children), index: villageIndex },
    });
  },

  getArrObj(arr) {
    const res = { arr: [], obj: [], index: 0 };
    arr.forEach(item => {
      res.arr.push(item.name);
      res.obj.push({ name: item.name, code: item.code });
    });
    return res;
  },

  submit() {
    const data = {
      name: this.data.name,
      address: this.data.address,
      testTime: this.data.time,
      contactNumber: this.data.contact,
      district: Number(this.data.area.obj[this.data.area.index].code),
      street: Number(this.data.street.obj[this.data.street.index].code),
      community: Number(this.data.village.obj[this.data.village.index].code),
    };

    if (data.name.length === 0) { 
      wx.showToast({ title: '请输入名称', icon: 'none' });
      return;
    }
    if (data.address.length === 0) {
      wx.showToast({ title: '请输入具体地址', icon: 'none' });
      return;
    }
    if (data.testTime.length === 0) {
      wx.showToast({ title: '请输入检测时间', icon: 'none' });
      return;
    }
    if (data.contactNumber.length === 0) {
      wx.showToast({ title: '请输入联系方式', icon: 'none' });
      return;
    }

    app.myrequest({
      url: '/nucleic-acids/testing-institutions',  // 请求的接口
      method: 'POST',
      data: data,
      success(res) {
        if (res.data.code === 200) {
          wx.showModal({
            content: '添加成功',
            icon: 'none'
          });
        } else {
          wx.showToast({
            title: '添加失败',
            icon: 'none'
          });
        }
      }
    });
  },
})
