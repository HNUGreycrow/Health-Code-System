// components/box.js
Component({

  /**
   * 接收父组件传值
   */
  properties: {
    created_at:{
      type:String
    },resulttext:{
      type:String
    },testing_organization:{
      type:String
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
		
  },

  /**
   * 组件的方法列表
   */
  methods: {
		goto(event){
		  const page = event.currentTarget.dataset.page;
			console.log(page)
		  wx.navigateTo({
		    url: '/pages/'+page+'/'+page,
		  })
		},
  }
})