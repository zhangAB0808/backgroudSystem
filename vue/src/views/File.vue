<template>
    <div>
        <div style="margin: 10px 0">
            <el-upload action="http://localhost:8081/file/upload"
                       :show-file-list="false" :on-success="handleFileUploadSuccess" style="display: inline-block">
                <el-button type="primary">上传文件<i class="el-icon-top ml-5"></i></el-button>
            </el-upload>
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
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="id" label="ID">
            </el-table-column>
            <el-table-column prop="name" label="文件名称">
            </el-table-column>
            <el-table-column prop="type" label="文件类型">
            </el-table-column>
            <el-table-column prop="size" label="文件大小">
            </el-table-column>
            <el-table-column label="下载">
                <template slot-scope="scope">
                    <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="enable" label="启用">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.enable" active-color="#13cee6" inactive-color="#ccc" @change="changeEnable(scope.row)">
                    </el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
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
    </div>
</template>

<script>
    export default {
        name: "File",
        data() {
            return {
                tableData: [],
                name: "",
                multipleSelection: [],
                pageNum: 1,
                pageSize: 10,
                total: 0
            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                this.request.get("/file/page", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name,
                    }
                }).then(res => {
                    console.log(res)
                    this.tableData = res.records
                    this.total = res.total
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
            del(id) {
                this.request.delete("/file/delete/" + id,).then(res => {
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
                this.request.post("/file/deleteBatch", ids).then(res => {
                    if (res) {
                        this.$message.success("批量删除成功！")
                        this.load()
                    } else {
                        this.$message.error("删除失败！")
                    }
                })
            },
            handleFileUploadSuccess(res) {
                console.log(res)
                this.load()
            },
            download(url) {
                window.open(url)
            },
            changeEnable(row){
                this.request.post('/file/update',row).then(res=>{
                    if(res.code ==="200"){
                        this.$message.success("操作成功！")
                    }else {
                        this.$message.error(res.msg)

                    }
                })
            }
        }
    }

</script>

<style scoped>

</style>