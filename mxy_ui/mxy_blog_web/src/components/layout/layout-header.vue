<template>
    <div id="layout-header">
        <!--导航栏 LOGO-->
        <div class="site-logo">
            <router-link to="/">
                <img src="@/assets/dw.svg" alt="">
            </router-link>
        </div>
        <!--导航栏 内容-->
        <div class="site-menus">
            <!--搜索按钮-->
            <div class="menu-item header-search">
                <header-search/>
            </div>
            <!--标题-->
            <div class="menu-item">
                <a href="#" style="padding: 12px 10px;
        color: #545454;
        font-weight: 500;
        font-size: 16px;">博客</a>
            </div>
        </div>
    </div>
</template>

<script>
    import HeaderSearch from '@/components/header-search'
    import {fetchCategory} from '../../api'

    export default {
        name: "layout-header",
        components: {HeaderSearch},
        data() {
            return {
                lastScrollTop: 0,
                fixed: false,
                hidden: false,
                category: [],
                mobileShow: false
            }
        },
        mounted() {
            this.fetchCategory()
        },
        methods: {
            fetchCategory() {
                /*fetchCategory().then(res => {
                    this.category = res.data
                }).catch(err => {
                    console.log(err)
                })*/
            }
        }
    }
</script>

<style scoped lang="less">
    /*头部 样式*/
    #layout-header {
        position: fixed;
        top: 0;
        z-index: 9;
        width: 100%;
        height: 80px;
        padding: 0 80px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: .3s all ease;
        -webkit-transition: .3s all ease;
        -moz-transition: .3s all linear;
        -o-transition: .3s all ease;
        -ms-transition: .3s all ease;
    }

    /*导航栏 LOGO样式*/
    .site-logo {
        text-align: center;
    }

    .site-logo img {
        width: 60px;
        height: 60px;
    }

    /*导航栏 样式*/
    .site-menus {
        display: flex;
        align-items: center;
    }

    .menu-item {
        min-width: 60px;
        height: 50px;
        line-height: 50px;
        text-align: center;
        position: relative;
    }

    /*自适应 宽度设置*/
    @media (max-width: 960px) {
        #layout-header {
            padding: 0 20px;
        }
    }

    @media (max-width: 600px) {
        #layout-header {
            padding: 0 10px;
        }
        .site-menus {
            position: absolute;
            display: none;
            visibility: hidden;
            background-color: #F9F9F9;
            width: 100%;
            left: 0;
            top: 80px;
            z-index: -9;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

        .menu-item {
            position: relative;
            height: unset;

        }

    }
    .site-menus.mobileShow {
        display: inline-block;
        visibility: visible;
        z-index: 99;
    }

    }
</style>
