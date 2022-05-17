import Vue from 'vue'
import VueRouter from 'vue-router'
import User from "@/views/User";
import Manage from "@/views/Manage";
import Home from "@/views/Home";
import Login from "@/views/Login";
import Register from "@/views/Register";
import People from "@/views/People";
import File from "@/views/File";
import Role from "@/views/Role";
import Menu from "@/views/Menu";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: Manage,
    redirect: "/home",
    children: [
      {path: 'user', name: 'User', component: User},
      {path: 'home', name: 'Home', component: Home},
      {path: 'people', name: 'People', component: People},
      {path: 'file', name: 'File', component: File },
      {path: 'role', name: 'Role', component: Role },
      {path: 'menu', name: 'Menu', component: Menu },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
