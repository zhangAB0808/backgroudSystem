<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
             background-color="rgb(48,65,86)" text-color="#fff" active-text-color="yellow"
             :collapse-transition="false"
             :collapse="isCollapse"
             router
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" style="width: 20px; position: relative; top: 5px;margin-right: 5px">
            <b style="color: white" v-show="logoTextShow">后台管理系统</b>
        </div>

<!--       动态绑定-->
        <div v-for="item in menus" :key="item.id">
            <div v-if="item.path">
                <el-menu-item :index="item.path">
                    <template slot="title">
                        <i :class="item.icon"></i>
                        <span slot="title">{{item.name}}</span>
                    </template>
                </el-menu-item>
            </div>
            <div v-else>
                <el-submenu :index="item.id+''">
                    <template slot="title"><i :class="item.icon"></i>
                        <span slot="title">{{item.name}}</span>
                    </template>
                    <div v-for="subItem in item.children" :key="subItem.id">
                        <div v-if="subItem.path">
                            <el-menu-item :index="subItem.path">
                                <template slot="title">
                                    <i :class="subItem.icon"></i>
                                    <span slot="title">{{subItem.name}}</span>
                                </template>
                            </el-menu-item>
                        </div>
                    </div>
                </el-submenu>
            </div>

        </div>


        <!--        <el-submenu index="2">-->
        <!--            <template slot="title"><i class="el-icon-menu"></i>-->
        <!--                <span slot="title">系统管理</span>-->
        <!--            </template>-->
        <!--                <el-menu-item index="/user">-->
        <!--                    <i class="el-icon-s-custom"></i>-->
        <!--                    <span slot="title">用户管理</span>-->
        <!--                </el-menu-item>-->
        <!--                <el-menu-item index="/role">-->
        <!--                    <i class="el-icon-s-custom"></i>-->
        <!--                    <span slot="title">角色管理</span>-->
        <!--                </el-menu-item>-->
        <!--                <el-menu-item index="/menu">-->
        <!--                    <i class="el-icon-s-custom"></i>-->
        <!--                    <span slot="title">菜单管理</span>-->
        <!--                </el-menu-item>-->
        <!--                <el-menu-item index="/file">-->
        <!--                    <i class="el-icon-document"></i>-->
        <!--                    <span slot="title">文件管理</span>-->
        <!--                </el-menu-item>-->
        <!--        </el-submenu>-->

    </el-menu>
</template>

<script>
    export default {
        name: "Aside",
        props: {
            isCollapse: Boolean,
            logoTextShow: Boolean,
        },
        data() {
            return {
                menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : []
            }
        }
    }
</script>

<style scoped>

</style>