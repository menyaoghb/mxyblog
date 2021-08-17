<template>
    <div class="home">
        <banner isHome="true"></banner>
        <div class="site-content animate" style="width: 100%;">
            <!--通-->
            <div style="margin-top: 100px;    text-align: center;">
                <div style="display: inline-block;vertical-align: middle;">
                    <h1 style="font-size: 32px;padding-bottom: 30px;position: relative;font-weight: 500;color: #000000;">
                        About</h1>
                    <p style="margin-top: 20px;line-height: 22px;color: #888;">
                        The avenue is simple,
                        <br>and the rich soul is from this.
                    </p>
                </div>
            </div>
            <!--焦点图-->
            <!--            <div class="top-feature" v-if="!hideSlogan"
                             style="background-image: url('http://xyule.top/home_page.jpg');    height: 600px;">
                            <div style="width: 25%;float: left">



                                <a>
                                    <div style="width: 280px;height: 400px;background-color: red;">
                                        <h1 style="">Our Solutions</h1>
                                        <p class="paragraph-5">We measure the drivers behind sustainable farmland, helping you make
                                            decisions that benefit your fields and the planet.<br></p><a href="/solutions"
                                                                                                         class="button w-button">Learn&nbsp;More</a>
                                    </div>
                                </a>
                            </div>
                            <div style="width: 25%;float: left">
                                <a>
                                    <div style="width: 280px;height: 400px;background-color: red;"></div>
                                </a>
                            </div>
                            <div style="width: 25%;float: left">
                                <a>
                                    <div style="width: 280px;height: 400px;background-color: red;"></div>
                                </a>
                            </div>
                            <div style="width: 25%;float: left">
                                <a>
                                    <div style="width: 280px;height: 400px;background-color: red;"></div>
                                </a>
                            </div>


                            <section-title>
                                <div style="display: flex;align-items: flex-end;">聚焦
                                    <small-ico></small-ico>
                                </div>
                            </section-title>
                            <div class="feature-content">
                                <div class="feature-item" v-for="item in features" :key="item.title">
                                    <Feature :data="item"></Feature>
                                </div>
                            </div>
                        </div>-->
            <!--文章列表-->
            <div>
                <main class="site-main" :class="{'search':hideSlogan}" style="width: 100%;">
                    <!--<section-title v-if="!hideSlogan">推荐</section-title>-->
                    <template v-for="item in postList">
                        <post :post="item" :key="item.id"></post>
                    </template>
                </main>
            </div>


            <!--加载更多-->
            <!--            <div class="more" v-show="hasNextPage">
                            <div class="more-btn" @click="loadMore">More</div>
                        </div>-->
        </div>
    </div>
</template>

<script>
    import Banner from '@/components/banner'
    import Feature from '@/components/feature'
    import sectionTitle from '@/components/section-title'
    import Post from '@/components/post'
    import SmallIco from '@/components/small-ico'
    import Quote from '@/components/quote'
    import {fetchFocus, fetchList} from '../api'

    export default {
        name: 'Home',
        props: ['cate', 'words'],
        data() {
            return {
                features: [],
                postList: [],
                currPage: 1,
                hasNextPage: false
            }
        },
        components: {
            Banner,
            Feature,
            sectionTitle,
            Post,
            SmallIco,
            Quote
        },
        computed: {
            searchWords() {
                return this.$route.params.words
            },
            category() {
                return this.$route.params.cate
            },
            hideSlogan() {
                return this.category || this.searchWords
            },
            notice() {
                return this.$store.getters.notice
            }
        },
        methods: {
            fetchFocus() {
                fetchFocus().then(res => {
                    this.features = res.data || []
                }).catch(err => {
                    console.log(err)
                })
            },
            fetchList() {
                fetchList().then(res => {
                    this.postList = res.data.items || []
                    this.currPage = res.data.page
                    this.hasNextPage = res.data.hasNextPage
                }).catch(err => {
                    console.log(err)
                })
            },
            loadMore() {
                fetchList({page: this.currPage + 1}).then(res => {
                    this.postList = this.postList.concat(res.data.items || [])
                    this.currPage = res.data.page
                    this.hasNextPage = res.data.hasNextPage
                })
            }
        },
        mounted() {
            this.fetchFocus();
            this.fetchList();
        }
    }
</script>
<style scoped lang="less">

    .site-content {

    .notify {
        margin: 60px 0;
        border-radius: 3px;

    &
    > div {
        padding: 20px;
    }

    }


    .search-result {
        padding: 15px 20px;
        text-align: center;
        font-size: 20px;
        font-weight: 400;
        border: 1px dashed #ddd;
        color: #828282;
    }

    }

    .top-feature {
        width: 100%;
        height: auto;
        margin-top: 30px;

    .feature-content {
        margin-top: 10px;
        display: flex;
        justify-content: space-between;
        position: relative;

    .feature-item {
        width: 32.9%;
    }

    }
    }

    .site-main {
        padding-top: 80px;

    &
    .search {
        padding-top: 0;
    }

    }

    .more {
        margin: 50px 0;

    .more-btn {
        width: 100px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        color: #ADADAD;
        border: 1px solid #ADADAD;
        border-radius: 20px;
        margin: 0 auto;
        cursor: pointer;

    &
    :hover {
        color: #8fd0cc;
        border: 1px dashed #8fd0cc;
    }

    }
    }

    /******/
    @media (max-width: 800px) {
        .top-feature {
            display: none;
        }

        .site-main {
            padding-top: 40px;
        }

        .site-content {

        .notify {
            margin: 30px 0 0 0;
        }

        .search-result {
            margin-bottom: 20px;
            font-size: 16px;
        }
    }

    }

    /******/
</style>
