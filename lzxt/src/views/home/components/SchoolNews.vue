<template>
  <el-row :gutter="20">
    <el-col :sm="12" :xs="0">
      <img class="cover" src="@/images/covers/1.jpg" alt="学堂资讯"/>
    </el-col>
    <el-col :sm="12" :xs="24" class="content-cell">
      <section-header title="学堂资讯" more="/news"/>
      <div class="content">
        <el-tabs class="school-news-tab" v-model="activeTab" @tab-click="hanleTabActive">
          <el-tab-pane label="题库" name="1"><news-tab-pane :list="tabOneList" /></el-tab-pane>
          <el-tab-pane label="升学" lazy name="2"><news-tab-pane :list="tabTwoList" /></el-tab-pane>
          <el-tab-pane label="其他" lazy name="3"><news-tab-pane :list="tabThreeList" /></el-tab-pane>
        </el-tabs>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

import NewsTabPane from './NewsTabPane.vue'

export default {
  name: 'school-news',
  components: { NewsTabPane },
  computed: {
    ...mapGetters('news', {
      tabOneList: 'tabOneList',
      tabTwoList: 'tabTwoList',
      tabThreeList: 'tabThreeList'
    })
  },
  data () {
    return {
      activeTab: '1',
      activedTabs: [1]
    }
  },
  mounted () {
    this.initTabList(1)
  },
  methods: {
    ...mapActions('news', ['getNewsList']),
    initTabList (tabIndex) {
      this.getNewsList({ newsType: tabIndex }).then(() => {
        this.activedTabs.push(tabIndex)
      })
    },
    hanleTabActive (tab) {
      const tabIndex = parseInt(tab.name, 10)
      if (this.activedTabs.indexOf(tabIndex) === -1) {
        this.initTabList(tabIndex)
      }
    }
  }
}
</script>
<style lang="scss">
.school-news-tab {

  .el-tabs__nav-wrap::after{
    background-color: transparent;
  }

  .el-tabs__content {
    height: 250px;
  }
}
</style>

<style lang="scss" scoped>
@import "~@/styles/vars.scss";
@import '~@/styles/mixins.scss';

.cover {
  max-width: 100%;
  max-height: 100%;
}

.content {
  padding: 30px 20px;
}
</style>

<style lang="scss">
  @media screen and (max-width: 768px) {
    .school-news-tab {
      .content-cell { padding: 0 20px !important;}
      .content { padding: 0; }
      .el-tabs__content { max-height: 250px; height: auto;}
    }

  }
</style>
