<template>
    <div style=" font-size: 12px;line-height: 60px;display: flex">
        <div style="flex: 1;">
            <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
            <el-breadcrumb separator="/" style=" display: inline-block;margin-left: 10px">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 100px; cursor: pointer">
            <div style="display: inline-block">
                <img :src="user.avatarUrl" alt="" style="width: 31px;height: 31px;border-radius: 50%;position: relative;top: 10px;right: 5px;">
                <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item style="font-size: 14px;">
                    <router-link to="/people" style="text-decoration: none">个人信息</router-link>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px;">
                    <span style="text-decoration: none" @click="logout">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        name: "Header",
        props: {
            collapseBtnClass: String,
            collapse: Function,
            user: Object
        },
        data(){
            return{

            }
        },
        methods:{
            logout(){
                this.$router.push("/login")
                localStorage.removeItem("user")
                localStorage.removeItem("menus")
                this.$message.success("退出成功!")
            }
        }
    }
</script>

<style scoped>

</style>