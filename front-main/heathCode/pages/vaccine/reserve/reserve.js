const app = getApp();

Page({
  data: {
    centerName: '', // 用来保存接收到的接种点名称
    vaccDate: '',    // 保存用户选择的日期
    showPicker: false, // 控制时间选择器的显示与隐藏
  },

  onLoad(options) {
    // 获取传递的参数
    if (options.centerName) {
      this.setData({
        centerName: options.centerName,
      });
    }
  },

  // 显示日期选择器
  showDatePicker() {
    this.setData({
      showPicker: true,
    });
  },

  // 隐藏日期选择器
  cancelDatePicker() {
    this.setData({
      showPicker: true,
    });
  },

  // 确认日期选择
  confirmDatePicker() {
    this.setData({
      showPicker: true,
    });
  },

  // 绑定日期选择
  bindDateInput(e) {
    const selectedDate = e.detail.value;
    this.setData({
      vaccDate: selectedDate,
    });
  },

  // 提交预约信息
  submit() {
    const { centerName, vaccDate } = this.data;

    // 校验日期是否选择
    if (!vaccDate) {
      wx.showToast({
        title: '请选择预约日期',
        icon: 'none',
      });
      return;
    }

    // 调用接口提交数据
    app.myrequest({
      url: '/vaccine-inoculation/vaccinationRecord',
      method: 'POST',
      data: {
        name: centerName,
        vaccDate: vaccDate,
      },
      success(res) {
        if (res.data.code === 200) {
          wx.showToast({
            title: '预约成功',
            icon: 'success',
            duration: 2000,
          });
					setTimeout(() => {
					  wx.navigateBack();
					}, 2000);
        } else {
          wx.showToast({
            title: '预约失败，请重试',
            icon: 'none',
          });
        }
      },
      fail() {
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none',
        });
      },
    });
  },
});
