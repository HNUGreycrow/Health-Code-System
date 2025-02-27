const app = getApp()
const villages = require('../../utils/villages');  // 引入villages数据

Page({
  data: {
    districts: [], // 地区选择项
    streets: [], // 街道选择项
    communities: [], // 社区选择项

    selectedDistrict: '', // 默认选择的地区
    selectedStreet: '', // 默认选择的街道
    selectedCommunity: '', // 默认选择的社区

    testingInstitutes: [] // 核酸检测机构列表
  },

  // 页面加载时初始化选择项
  onLoad() {
    const districts = this.extractDistricts(villages.children);
    this.setData({
      districts: districts,
      selectedDistrict: '' // 默认不选择任何地区
    });
    this.loadTestingInstitutes(); // 加载核酸检测机构
  },

  // 提取地区名称
  extractDistricts(data) {
    return data.map(item => item.name);
  },

  // 地区选择变化时
  onDistrictChange(e) {
    const selectedDistrict = this.data.districts[e.detail.value];
    this.setData({
      selectedDistrict,
      selectedStreet: '', // 重置街道和社区
      selectedCommunity: ''
    });
    this.loadStreets(selectedDistrict);
  },
	
	onStreetChange(e) {
		const selectedStreet = this.data.streets[e.detail.value];
		this.setData({
			selectedStreet,
			selectedCommunity: ''
		})
		this.loadCommunities(this.findData(villages.children, this.data.selectedDistrict));
	},

  // 加载街道信息
  loadStreets(district) {
    const districtData = this.findData(villages.children, district);
    const streets = districtData ? districtData.children.map(item => item.name) : [];
    this.setData({
      streets,
      selectedStreet: streets[0] || '' // 默认选择第一个街道
    });
    this.loadCommunities(districtData);
  },

  // 根据地区名称查找数据
  findData(data, name) {
    return data.find(item => item.name === name);
  },

  // 加载社区信息
  loadCommunities(districtData) {
    const streetsData = districtData ? districtData.children.find(street => street.name === this.data.selectedStreet) : null;
    const communities = streetsData ? streetsData.children.map(item => item.name) : [];
    this.setData({
      communities,
      selectedCommunity: communities[0] || '' // 默认选择第一个社区
    });
    this.loadTestingInstitutes();
  },

  // 社区选择变化时
  onCommunityChange(e) {
    this.setData({
      selectedCommunity: this.data.communities[e.detail.value]
    });
    this.loadTestingInstitutes();
  },

  // 获取选中区域的code
  getAreaCode(districtName, streetName, communityName) {
    const districtData = this.findData(villages.children, districtName);
    const streetData = districtData ? districtData.children.find(street => street.name === streetName) : null;
    const communityData = streetData ? streetData.children.find(community => community.name === communityName) : null;

    return {
      districtCode: districtData ? districtData.code : '',
      streetCode: streetData ? streetData.code : '',
      communityCode: communityData ? communityData.code : ''
    };
  },

  // 加载核酸检测机构
  loadTestingInstitutes() {
    const { selectedDistrict, selectedStreet, selectedCommunity } = this.data;

    if (!selectedDistrict && !selectedStreet && !selectedCommunity) {
      // 如果没有选择任何区域，调用默认接口
      app.myrequest({
        url: '/nucleic-acids/testing-institutions', // 无请求参数
        method: 'GET',
        success: (res) => {
          this.setData({
            testingInstitutes: res.data.data
          });
					console.log(res)
        },
        fail: (err) => {
          wx.showToast({
            title: '加载失败，请重试',
            icon: 'none'
          });
        }
      });
    } else {
      // 如果选择了区域，调用带参数的接口
      const { districtCode, streetCode, communityCode } = this.getAreaCode(selectedDistrict, selectedStreet, selectedCommunity);
      app.myrequest({
        url: '/nucleic-acids/testing-institutionsByArea',
        method: 'POST',
        data: {
          district: districtCode,
          street: streetCode,
          community: communityCode
        },
        success: (res) => {
          this.setData({
            testingInstitutes: res.data.data
          });
        },
        fail: (err) => {
          wx.showToast({
            title: '加载失败，请重试',
            icon: 'none'
          });
        }
      });
    }
  },

  onShow() {
    this.loadTestingInstitutes(); // 页面显示时加载核酸检测机构
  }
})
