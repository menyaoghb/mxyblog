<template>
    <div class="header-search">
        <i class="iconfont iconsearch" @click.stop="click"></i>
        <input ref="searchInput" :class="{'show':show}" v-model="keyWords" type="text" @click.stop="" @input="handleQuery"/>
    </div>
</template>

<script>
    import {bookmarkList} from "@/api";

    export default {
        name: "keyWord-search",
        data(){
          return{
              searchValue: '',
              show: false,
            keyWords: '',
            results: []
          }
        },
        watch:{
            show(value) {
                if (value) {
                    document.body.addEventListener('click', this.close)
                } else {
                    document.body.removeEventListener('click', this.close)
                }
            }
        },
        methods:{
            search(){
                // console.log(this.searchValue)
                this.$router.push({name:'search',params:{words:this.searchValue}});
                this.close()
            },
            click(){
                this.searchValue = ''
                this.show = !this.show
                if (this.show) {
                    this.$refs.searchInput && this.$refs.searchInput.focus()
                }
            },
            close(){
                this.$refs.searchInput && this.$refs.searchInput.blur()
                this.show = false
            },
          clearTimer() {
            if (this.timer) {
              clearTimeout(this.timer)
            }
          },
          handleQuery(event) {
            this.clearTimer()
            console.log(event.timeStamp)
            this.timer = setTimeout(() => {
              console.log(event.timeStamp)
              // console.log(this.lastTime)
              // if (this.lastTime - event.timeStamp === 0) {
              bookmarkList(this.listQuery.currentPage).then(res => {
                this.changeColor(res.data.records)
              }).catch(err => {
                console.log(err)
              })
              // }
            }, 2000)
          },

          changeColor(resultsList) {
            resultsList.map((item, index) => {
              // console.log('item', item)
              if (this.keyWords && this.keyWords.length > 0) {
                // 匹配关键字正则
                let replaceReg = new RegExp(this.keyWords, 'g')
                // 高亮替换v-html值
                let replaceString =
                    '<span class="search-text">' + this.keyWords + '</span>'
                resultsList[index].name = item.name.replace(
                    replaceReg,
                    replaceString
                )
              }
            })
            this.results = []
            this.results = resultsList
          }
        }
    }
</script>

<style scoped lang="less">
.header-search{
    display: inline-block;
    position: relative;
    i{
        font-size: 18px;
        position: relative;
        top: 3px;
    }
    input{
        border:none;
        outline:none;
        overflow: hidden;
        background: transparent;
        height: 30px;
        width: 0;
        transition: .2s all;
        &.show{
            width: 200px;
            margin-left: 10px;
        }
        &:focus{
            border-bottom: 1px solid #8fd0cc;
        }
    }
}
</style>
