import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/login.vue'
import admin_panel from '../views/admin_panel.vue'
import register from '../views/register.vue'
import home from '../views/home.vue'
import comments from '../views/comments.vue'
import activities from '../views/activities.vue'
import film_detail from '../views/film_detail.vue'
import ticket from '../views/ticket.vue'
import seat from '../views/seat.vue'
import personal_center from '../views/personal_center.vue'
import mainpage_test from '../views/mainpage_test.vue'
import header from '../components/layout/Header.vue'
import footer from '../components/layout/Footer.vue'
import Layout from '../components/layout/Layout.vue'
// 下面的是ADMIN_PANEL的引入
import admin_order_manage from '../views/admin_panel/admin_order_manage.vue'
Vue.use(VueRouter)

const routes = [
 
  {
    path: '/login',
    name: 'login',
    component: login
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    children:[
    {
        path:"",
        component:()=>import("@/views/home")
    },
    {
        path:"activities",
        component:()=>import("@/views/activities")
    },
    {
        path:"comment",
        component:()=>import("@/views/comments")
    },
    {
      path:"movie_page",
      component:()=>import("@/views/movie_page")
  },
  {
    path:"film_detail",
    component:()=>import("@/views/film_detail")
},
  {
    path:"ticket",
    component:()=>import("@/views/ticket")
  },
  {
    path:"seat",
    component:()=>import("@/views/seat")
  },
  {
    path:"personal_center",
    component:()=>import("@/views/personal_center")
  },
    ]
  },
  {
    path: '/register',
    name: 'register',
    component: register
  },
  {
    path: '/mainpage_test',
    name: 'mainpage_test',
    component: mainpage_test
  },
  {
    path: '/header_test',
    name: 'header',
    component: header
  },
  {
    path: '/footer_test',
    name: 'footer',
    component: footer
  },
  {
    path: '/admin_panel',
    name: 'admin_panel',
    component: admin_panel,
    children:[
      {
          path:"order_manage",
          component:()=>import("@/views/admin_panel/admin_order_manage")
      },
      {
        path:"order_error_report",
        component:()=>import("@/views/admin_panel/admin_order_error_report")
      },
      {
      path:"staff_add",
      component:()=>import("@/views/admin_panel/admin_staff_add")
      },
    ]
  },
]

const router = new VueRouter({
  routes
})

export default router
