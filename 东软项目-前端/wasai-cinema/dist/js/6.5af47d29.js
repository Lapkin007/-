"use strict";(self["webpackChunkwasai_cinema"]=self["webpackChunkwasai_cinema"]||[]).push([[6],{6006:function(t,e,s){s.r(e),s.d(e,{default:function(){return u}});var a=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"outbox"},[s("div",{staticClass:"header"},[s("el-row",[s("el-col",{staticClass:"header-item",attrs:{span:5}},[t._v("订单ID")]),s("el-col",{staticClass:"header-item",staticStyle:{"padding-left":"0px"},attrs:{span:4}},[t._v("上报人")]),s("el-col",{staticClass:"header-item",staticStyle:{"padding-left":"70px"},attrs:{span:5}},[t._v("异常处理状态")]),s("el-col",{staticClass:"header-item",staticStyle:{"padding-left":"170px"},attrs:{span:10}},[t._v("操作")])],1)],1),s("el-collapse",{model:{value:t.activeNames,callback:function(e){t.activeNames=e},expression:"activeNames"}},t._l(t.Oe_list,(function(e,a){return s("el-collapse-item",{key:a,staticStyle:{"border-bottom":"1px solid black"},attrs:{name:a,disabled:""}},[s("template",{slot:"title"},[s("el-row",{staticStyle:{"background-color":"white",width:"1800px",height:"48px"}},[s("el-col",{staticClass:"order-box-item1",attrs:{span:5}},[t._v(t._s(e.id))]),s("el-col",{staticClass:"order-box-item1",attrs:{span:6}},[t._v(t._s(e.reviewer))]),s("el-col",{staticClass:"order-box-item2",attrs:{span:5}},[s("div",{class:t.changeOrderCss(e)},[t._v(t._s(e.status))])]),s("el-col",{staticClass:"order-box-item3",attrs:{span:8}},[s("el-button",{staticClass:"wait1",on:{click:function(e){return t.Operation_SolveOrder(a)}}},[t._v("处理异常")]),s("el-button",{staticClass:"PadButton",on:{click:function(e){return t.changePad(a)}}},[t._v("展开收起")])],1)],1)],1),s("div",{staticClass:"collapse-panel-item"},[t._v("ID:"+t._s(e.id))]),s("div",{staticClass:"collapse-panel-item"},[t._v("订单ID:"+t._s(e.oid))]),s("div",{staticClass:"collapse-panel-item"},[t._v("异常原因:"+t._s(e.reason))]),s("div",{staticClass:"collapse-panel-item"},[t._v("上报人:"+t._s(e.reviewer))]),s("div",{staticClass:"collapse-panel-item"},[t._v("是否已处理完成:"+t._s(e.status))]),s("div",{staticClass:"collapse-panel-item"},[t._v("处理结果:"+t._s(e.result))]),s("div",{staticClass:"collapse-panel-item"},[t._v("创建时间:"+t._s(e.createTime))])],2)})),1),s("el-dialog",{attrs:{title:"异常上报",visible:t.dialogSolveVisible},on:{"update:visible":function(e){t.dialogSolveVisible=e}}},[s("el-form",{attrs:{model:t.form}},[s("el-form-item",{attrs:{label:"处理结果","label-width":t.formLabelWidth}},[s("el-input",{attrs:{autocomplete:"off"},model:{value:t.form.result,callback:function(e){t.$set(t.form,"result",e)},expression:"form.result"}})],1),s("el-form-item",{attrs:{label:"处理状态","label-width":t.formLabelWidth}},[s("el-select",{attrs:{placeholder:"请选择处理状态"},model:{value:t.form.statue,callback:function(e){t.$set(t.form,"statue",e)},expression:"form.statue"}},[s("el-option",{attrs:{label:"处理成功",value:"处理成功"}}),s("el-option",{attrs:{label:"等待处理",value:"等待处理"}})],1)],1)],1),s("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{on:{click:function(e){t.dialogSolveVisible=!1}}},[t._v("取 消")]),s("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.Operation_SolveOrder_confirm(t.form_index)}}},[t._v("确 定")])],1)],1)],1)},l=[],i=(s(6761),s(3584)),o=s(9254),r={data(){return{form_index:-1,formLabelWidth:"90px",dialogSolveVisible:!1,form:{name:"",reason:"",statue:"",result:""},activeNames:[],Oe_list:{},cancled:{cancle:!0,pay:!1,wait:!1,fail:!1},paid:{cancle:!1,pay:!0,wait:!1,fail:!1},waited:{cancle:!1,pay:!1,wait:!0,fail:!1},failed:{cancle:!1,pay:!1,wait:!1,fail:!0}}},methods:{LoadAllOe(){(0,i.fB)().then((t=>{if(200==t.code){console.log(t),this.Oe_list=t.data;for(var e=0;e<this.Oe_list.length;e++)0==this.Oe_list[e].status&&(this.Oe_list[e].status="等待处理"),1==this.Oe_list[e].status&&(this.Oe_list[e].status="处理成功"),this.Oe_list[e].createTime=(0,o.T)(new Date(this.Oe_list[e].createTime))}}))},changeOrderCss:function(t){return"已被撤销"==t.status?this.cancled:"处理成功"==t.status?this.paid:"处理异常"==t.status?this.waited:"等待处理"==t.status?this.failed:void 0},Operation_SolveOrder:function(t){this.dialogSolveVisible=!0,this.form_index=t},Operation_SolveOrder_confirm:function(t){"处理成功"==this.form.statue?(this.testlist[t].statue="处理成功",this.testlist[t].solve_statue=!0):this.testlist[t].statue="等待处理",this.testlist[t].solve_result=this.form.result,this.dialogSolveVisible=!1},changePad:function(t){-1==this.activeNames.indexOf(t)?this.activeNames.push(t):this.activeNames.pop(t)}},created:function(){this.LoadAllOe()}},c=r,n=s(1001),d=(0,n.Z)(c,a,l,!1,null,"09236592",null),u=d.exports}}]);
//# sourceMappingURL=6.5af47d29.js.map