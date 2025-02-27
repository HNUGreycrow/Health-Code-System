// pages/regist/regist.js
const app = getApp()
Page({
  submit(){
    const data = {
      name:this.data.name,
      identity_card:this.data.identity_card,
      phone_number:this.data.phone_number,
      address:this.data.address,
      district_id:Number(this.data.area.obj[this.data.area.index].code) ,
      street_id:Number(this.data.street.obj[this.data.street.index].code),
      community_id:Number(this.data.village.obj[this.data.village.index].code)
    }
    if(data.name.length == 0) 
      {wx.showToast({
        title: '请输入姓名', // 提示框的文字内容
        icon:'none'
      });return}
    if((data.identity_card.length) !== 18) 
      {wx.showToast({
        title: '请输入正确身份证号', // 提示框的文字内容
        icon:'none'
      });return}
    if((data.phone_number.length) !== 11) 
      {wx.showToast({
        title: '请输入正确手机号', // 提示框的文字内容
        icon:'none'
      });return}
    if(data.address.length == 0) 
      {wx.showToast({
        title: '请输入地址', // 提示框的文字内容
        icon:'none'
      });return}
    app.myrequest({
      url: '/health-code/applyCode',
      method:'POST',
      data:data,
      success(res) {
        if(res.data.code == 200)
        {
          wx.showModal({
            content: '申请成功',
            icon:'none',
            complete: () => {
              wx.navigateTo({
                url: '/pages/code/code',
              })
            }
          })
      }
      }
    })
  },


  data: {
    name:'',
    identity_card:'',
    phone_number:'',
    address:'',
    temp:{},
    area:{
      arr:[],
      obj:[],
      index:0
    },
    street:{
      arr:[],
      obj:[],
      index:0
    },
    village:{
      arr:[],
      obj:[],
      index:0
    }
  },


  bindKeyInput(e){
    if(e.target.dataset.index === "area")
    this.getpca(e.detail.value,0,0)
    if(e.target.dataset.index === "street")
      this.getpca(this.data.area.index,e.detail.value,0)
    if(e.target.dataset.index === "village")
      this.getpca(this.data.area.index,this.data.street.index,e.detail.value)
    if(e.target.dataset.index === "name")
      this.setData({
        name:e.detail.value
      })
    if(e.target.dataset.index === "identity_card")
      this.setData({
        identity_card:e.detail.value
      })
    if(e.target.dataset.index === "phone_number")
      this.setData({
          phone_number:e.detail.value
        })
    if(e.target.dataset.index === "address")
      this.setData({
          address:e.detail.value
      })
  },

  getpca(areaindex,streetindex,villageindex){
    this.setData({
      area:{
        ...this.getArrObj(this.data.temp.children),
        index:areaindex
      },
      street:{
        ...this.getArrObj(this.data.temp.children[areaindex].children),
        index:streetindex
      },
      village:{
        ...this.getArrObj(this.data.temp.children[areaindex].children[streetindex].children),
        index:villageindex
      }
    })
  },
  getArrObj(arr){
    const res = {
      arr:[],
      obj:[],
      index:0,
    }
    arr.forEach(
        item=>{
        res.arr.push(item.name)
        res.obj.push({name:item.name,code:item.code})
    })
    return res
  },
  onShow(){
    const temp = require('../../utils/villages')
    this.setData({
      temp
    })
    this.getpca(0,0,0)
  }
})