<template>
    <div>
      <v-layout class="px-3 pb-2">
        <v-flex xs2>
          <v-btn color="info">新增品牌</v-btn>
        </v-flex>
        <v-spacer></v-spacer>
        <v-flex xs3>
          <v-text-field label="搜索" single-line hide-details append-icon="search" v-model="key"></v-text-field>
        </v-flex>
      </v-layout>

      <v-data-table
        :headers="headers"
        :items="brands"
        :pagination.sync="pagination"
        :total-items="totalBrands"
        :loading="loading"
        class="elevation-1"
      >
        <!--每一行数据的渲染模板-->
        <template slot="items" slot-scope="props">
          <td class="text-xs-center">{{ props.item.id }}</td>
          <td class="text-xs-center">{{ props.item.name }}</td>
          <td class="text-xs-center"><img :src="props.item.image" alt=""></td>
          <td class="text-xs-center">{{ props.item.letter }}</td>
          <td class="text-xs-center">
            <v-btn flat icon color="info">
              <v-icon>edit</v-icon>
            </v-btn>
            <v-btn flat icon color="error">
              <v-icon>delete</v-icon>
            </v-btn>
          </td>
        </template>
      </v-data-table>
    </div>
</template>

<script>
    export default {
        name: "MyBrand",
      data(){
          return{
            headers:[
              {text: "品牌id", value: "id", align: 'center', sortable: true},
              {text: "品牌名称", value: "name", align: 'center', sortable: false},
              {text: "品牌LOGO", value: "image", align: 'center', sortable: false},
              {text: "品牌首字母", value: "letter", align: 'center', sortable: true},
              {text: "操作",  align: 'center', sortable: false},
            ],
            brands:[],
            pagination:{},
            totalBrands:0,
            loading:false,
            key:"", //搜索条件
          }
      },
      created() {
          this.brands=[
            {id: 2023, name: "OPPO", image:"1.jpg", letter:"O"},
            {id: 2024, name: "华为", image:"2.jpg", letter:"H"},
          ];
          this.totalBrands = 15;


        //去后台查询
        this.loadBrands();
      },
      watch:{//每当其改变就发请求
        key(){
          this.pagination.page = 1;//查询后跳到第一页
          this.loadBrands();
        },
        pagination:{
          deep:true,
          handler(){
            this.loadBrands();
          }
        }
      },
      methods:{
          loadBrands(){
            this.loading = true; //进度条
            this.$http.get("/item/brand/page",{
              params:{
                //搜索条件
                page: this.pagination.page,//vuetify提供的page 当前页
                rows: this.pagination.rowsPerPage,//每页大小
                sortBy: this.pagination.sortBy,//排序字段
                desc: this.pagination.descending,//是否降序
                key: this.key//搜索条件
              }
            }).then(resp => {
              this.brands = resp.data.items;
              this.totalBrands = resp.data.total;
              this.loading = false; //进度条
            })
          }
      }
    }
</script>

<style scoped>

</style>
