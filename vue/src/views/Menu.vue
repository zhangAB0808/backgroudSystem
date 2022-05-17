<template>
    <div>

        <div class="pd-10">
            <el-input style="width: 200px" placeholder="请输入名称" v-model="name"
                      suffix-icon="el-icon-search"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline ml-5"></i>
            </el-button>
            <el-popconfirm
                    class="ml-5 mr-5"
                    confirm-button-text='确定'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定删除吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline ml-5"></i></el-button>
            </el-popconfirm>
        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg"
                  row-key="id" default-expand-all
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="80">
            </el-table-column>
            <el-table-column prop="name" label="名称">
            </el-table-column>
            <el-table-column prop="path" label="路径">
            </el-table-column>
            <el-table-column  label="图标">
                <template slot-scope="scope">
                    <i :class="scope.row.icon" style="font-size: 18px;"></i>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="描述">
            </el-table-column>

            <el-table-column label="操作" width="300px">
                <template slot-scope="scope">
                    <el-button type="primary" @click="addChildren(scope.row.id)"
                               v-if="!scope.row.pid && !scope.row.path">新增子菜单<i class="el-icon-plus ml-5"></i>
                    </el-button>
                    <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit ml-5"></i>
                    </el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline ml-5"></i>
                        </el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="名称">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="路径">
                    <el-input v-model="form.path" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="图标">
                    <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%;">
                        <el-option
                                v-for="item in options"
                                :key="item.name"
                                :label="item.name"
                                :value="item.value">
                            <i :class="item.value" class="mr-5"></i>{{item.name}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.description" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "User",
        data() {
            return {
                tableData: [],
                form: {},
                name: "",
                dialogFormVisible: false,
                multipleSelection: [],
                options: []
            }
        },
        created() {
            //请求分页查询数据
            this.load()
        },
        methods: {
            load() {
                this.request.get("/menu", {
                    params: {
                        name: this.name
                    }
                }).then(res => {
                    console.log(res)
                    this.tableData = res.data
                })
            },

            reset() {
                this.name = ""
                this.load()
            },
            handleAdd() {
                this.dialogFormVisible = true
                this.form = {}
            },
            save() {
                this.request.post("/menu/save", this.form).then(res => {
                    if (res) {
                        this.$message.success("保存成功！")
                        this.dialogFormVisible = false
                    } else {
                        this.$message.error("保存失败！")
                    }
                    this.load()
                })
            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogFormVisible = true
                //请求图标的数据
                this.request.get("/menu/icons").then(res => {
                    this.options = res.data
                })
            },
            del(id) {
                this.request.delete("/menu/delete/" + id,).then(res => {
                    if (res) {
                        this.$message.success("删除成功！")
                        this.load()
                    } else {
                        this.$message.error("删除失败！")
                    }
                })
            },
            handleSelectionChange(val) {
                console.log(val)
                this.multipleSelection = val;
            },
            delBatch() {
                let ids = this.multipleSelection.map(v => v.id); //[1,2,3]
                this.request.post("/menu/deleteBatch", ids).then(res => {
                    if (res) {
                        this.$message.success("批量删除成功！")
                        this.load()
                    } else {
                        this.$message.error("删除失败！")
                    }
                })
            },
            addChildren(pid) {
                this.dialogFormVisible = true
                this.form = {}
                if (pid) {
                    this.form.pid = pid
                }
            }

        }
    }
</script>

<style>
    .headerBg {
        background-color: rgba(204, 204, 204, 0.78) !important
    }
</style>