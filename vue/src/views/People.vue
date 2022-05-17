<template>
    <div>
        <el-card style="width: 500px;padding: 20px">
            <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8081/file/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    >
                <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-form label-width="80px" size="small">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="form.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">确 定</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "People",
        data() {
            return {
                form: {},
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            }
        },
        created() {
            this.request.get("/user/username/" + this.user.username).then(res => {
                if (res.code === "200") {
                    console.log(res.data)
                    this.form = res.data
                }
            })
        },
        methods: {
            save() {
                this.request.post("/user/save", this.form).then(res => {
                    if (res) {
                        this.$message.success("保存成功！")
                        this.$emit('refreshUser')
                    } else {
                        this.$message.error("保存失败！")
                    }
                })
            },
            handleAvatarSuccess(res) {
                this.form.avatarUrl = res
            },

        }
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        left: 140px;
        bottom: 15px;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
        position: relative;
        top: 70px;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>