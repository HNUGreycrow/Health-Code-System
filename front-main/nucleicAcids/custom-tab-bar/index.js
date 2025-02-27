Component({
  data: {
    selected: 0,
    color: "#383838",
    selectedColor: "#476FDE",
    list: [{
      "pagePath": "/pages/sample/sample",
      "text": "采样",
      "iconPath": "/images/sample.png",
      "selectedIconPath": "/images/sample-active.png"
    },{
      "pagePath": "/pages/upload/upload",
      "text": "录入",
      "iconPath": "/images/upload.png",
      "selectedIconPath": "/images/upload-active.png"
    },{
      "pagePath": "/pages/point/point",
      "text": "检测点",
      "iconPath": "/images/point.png",
      "selectedIconPath": "/images/point-active.png"
      
    }]
  },
  attached() {
  },
  methods: {
    switchTab(e) {
      const data = e.currentTarget.dataset
      // if(data.index == 2){
      //   wx.showToast({
      //     title: '暂未开放，尽情期待',
      //     icon:'none'
      //   })
      //   return
      // }
      console.log(data.index);
      const url = data.path
      this.setData({
              selected: data.index
            }, () => {
              // 在 setData 回调函数中执行 wx.switchTab，确保数据已经更新
              wx.switchTab({
                url: url,
                success: () => {
                  console.log(url);
                  console.log(this.data.selected);
                }
              });
            });
    }
  },
  
})