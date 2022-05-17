<template>
    <el-container style="height: 100%;">

        <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);height: 700px;overflow: hidden">
            <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
        </el-aside>

        <el-container>
            <el-header>
                <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
            </el-header>

            <el-main>
                <router-view @refreshUser="getUser"/>  <!--表示当前页面的子路由会<router-view/>展示-->
            </el-main>
        </el-container>
    </el-container>

</template>

<style>
    .el-header {
        background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
    }

    .el-aside {
        color: #333;
    }


</style>

<script>
    import Aside from "@/components/Aside";
    import Header from "@/components/Header";
    export default {
        name: "Manage",
        data() {
            return {
                collapseBtnClass: 'el-icon-s-fold',
                isCollapse: false,
                sideWidth: 200,
                logoTextShow: true,
                user: {},
            }
        },
        components: {
            Aside,
            Header
        },
        created() {
            this.getUser()
        },
        methods: {
            collapse() {
                this.isCollapse = !this.isCollapse;
                if (this.isCollapse) {
                    this.sideWidth = 64
                    this.collapseBtnClass = 'el-icon-s-unfold'
                    this.logoTextShow = false
                } else {
                    this.sideWidth = 200
                    this.collapseBtnClass = 'el-icon-s-fold'
                    this.logoTextShow = true
                }
            },
            getUser(){
                let username = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:"";
                this.request.get("/user/username/"+username).then(res=>{
                   this.user= res.data
                })
            }

        }
    };
</script>
