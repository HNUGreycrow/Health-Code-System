const app = getApp()

Page({
  data: {
    question: '', // 用户输入的疑问
    imageList: [] // 存储用户上传的图片列表
  },

  // 处理用户输入的问题描述
  onQuestionInput(e) {
    this.setData({
      question: e.detail.value
    });
  },

  // 选择图片
  chooseImage(e) {
    const index = e.currentTarget.dataset.index; // 获取点击的图片索引
    const imageList = this.data.imageList;
    
    if (imageList.length >= 2 && !imageList[index]) {
      return; // 如果已经选择了两张图片且当前点击位置为空，不做任何操作
    }

    wx.chooseImage({
      count: 1, // 限制选择图片的数量为1
      sizeType: ['original', 'compressed'], // 可以选择原图和压缩图
      sourceType: ['album', 'camera'], // 可以选择相册和拍照
      success: (res) => {
        const tempFilePath = res.tempFilePaths[0];
        
        // 将图片转换为 Base64
        this.convertImageToBase64(tempFilePath, index);
      }
    });
  },

  // 将图片转换为 Base64
  convertImageToBase64(tempFilePath, index) {
    const fs = wx.getFileSystemManager();

    fs.readFile({
      filePath: tempFilePath,
      encoding: 'base64',
      success: (res) => {
        const base64Data = `data:image/jpeg;base64,${res.data}`; // 将 Base64 数据添加 MIME 类型
        this.updateImageList(base64Data, index); // 更新图片列表
      },
      fail: (err) => {
        wx.showToast({
          title: '图片转换失败，请重试',
          icon: 'none'
        });
      }
    });
  },

  // 更新图片列表
  updateImageList(base64Data, index) {
    const imageList = this.data.imageList;
    imageList[index] = base64Data; // 替换对应位置的图片为 Base64 格式
    this.setData({
      imageList: imageList // 更新图片列表
    });
  },

  // 提交反馈
  submitFeedback() {
    const { question, imageList } = this.data;
    if (!question) {
      wx.showToast({
        title: '请填写问题描述',
        icon: 'none'
      });
      return;
    }

    // 提交数据到后端（这里只是模拟请求）
    app.myrequest({
      url: '/health-code/appeal',
      method: 'POST',
      data: {
        appealReason: this.data.question,
        appealMaterials: imageList[0] // 提交 Base64 编码的图片
      },
      success: (res) => {
        wx.showToast({
          title: '反馈提交成功',
          icon: 'success',
          duration: 2000
        });
        setTimeout(() => {
          wx.navigateBack();
        }, 2000);
      },
      fail: (err) => {
        wx.showToast({
          title: '提交失败，请重试',
          icon: 'none'
        });
      }
    });
  }
})
