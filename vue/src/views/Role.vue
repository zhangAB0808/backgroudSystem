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
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="80">
            </el-table-column>
            <el-table-column prop="name" label="名称">
            </el-table-column>
            <el-table-column prop="description" label="描述">
            </el-table-column>
            <el-table-column prop="flag" label="唯一标识">
            </el-table-column>

            <el-table-column label="操作" width="280px">
                <template slot-scope="scope">
                    <el-button type="info" @click="selectMenu(scope.row.id)">分配菜单<i class="el-icon-menu ml-5"></i>
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
        <el-dialog title="菜单分配" :visible.sync="MenuDialogVisible" width="30%">
            <el-tree
                    :props="props"
                    :data="menuData"
                    show-checkbox
                    ref="tree"
                    node-key="id"
                    :default-expanded-keys="expands"
                    :default-checked-keys="checks"
            >
                 <span class="custom-tree-node" slot-scope="{ node, data }">
                     <span><i :class="data.icon"></i> {{ data.name }}</span></span>
            </el-tree>
            <div slot="footer" class="dialog-footer">
                <el-button @click="MenuDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
            </div>
        </el-dialog>

        <div class="pd-10">
            <div class="block">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="pageNum"
                        :page-sizes="[2, 5, 10, 20]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>
        </div>

        <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="名称">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.description" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="唯一标识">
                    <el-input v-model="form.flag" autocomplete="off"></el-input>
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
                total: 0,
                pageNum: 1,
                pageSize: 5,
                form: {},
                name: "",
                dialogFormVisible: false,
                MenuDialogVisible: false,
                multipleSelection: [],
                menuData: [],
                props: {
                    label: 'name',
                },
                expands: [],
                checks: [],
                roleId: 0
            }
        },
        created() {
            //请求分页查询数据
            this.load()
        },
        methods: {
            load() {
                this.request.get("/role/page", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name
                    }
                }).then(res => {
                    console.log(res)
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.load()
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
                this.request.post("/role/save", this.form).then(res => {
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
            },
            del(id) {
                this.request.delete("/role/delete/" + id,).then(res => {
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
                this.request.post("/role/deleteBatch", ids).then(res => {
                    if (res) {
                        this.$message.success("批量删除成功！")
                        this.load()
                    } else {
                        this.$message.error("删除失败！")
                    }
                })
            },
            selectMenu(rowId) {
                this.MenuDialogVisible = true
                this.roleId = rowId
                //请求菜单数据
                this.request.get("/menu").then(res => {
                    this.menuData = res.data
                    //把后台返回的数据转换成id数据
                    this.expands = this.menuData.map(v => v.id)
                })

                this.request.get("/role/getRoleMenu/" + this.roleId).then(res => {
                    this.checks = res.data
                })
            },
            saveRoleMenu() {
                this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
                    if (res.code === "200") {
                        this.$message.success("绑定成功")
                        this.MenuDialogVisible = false
                    } else {
                        this.$message.error("res.msg")
                    }
                })
            },
        }
    }
</script>

<style>
    .headerBg {
        background-color: rgba(204, 204, 204, 0.78) !important
    }
</style>