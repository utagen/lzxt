<template>
  <section class="learn-list-wrap">
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的学习</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <empty-list v-if="isEmpty">
      <span>少壮不努力，老大徒伤悲，少年，感觉去学习吧！</span>
    </empty-list>
    <div class="grid-wrap" v-else>
      <my-learn-list-item v-for="item in learnList" :item="item" :key="item.id" />
    </div>
    <div class="footer-wrap" v-if="showFooter">
        <el-pagination
          layout="total, prev, pager, next, jumper"
          hide-on-single-page
          :current-page.sync="page"
          :page-size="pageSize"
          :total="total"
          @current-change="switchPagination">
        </el-pagination>
      </div>
  </section>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import MyLearnListItem from './LearnListItem'

export default {
  name: 'learn-list',
  components: { MyLearnListItem },
  data () {
    return { page: 1, pageSize: 10 }
  },
  computed: {
    ...mapState('course', {
      learnList: state => state.learnCourseList.list,
      total: state => state.learnCourseList.total
    }),
    isEmpty () {
      return this.learnList.length === 0
    },
    showFooter () {
      return this.total > this.pageSize
    }
  },
  mounted () {
    const { page, pageSize } = this
    this.getLearnList({ page, pageSize })
  },
  methods: {
    ...mapActions('course', { getLearnList: 'getLearnCourseList' }),
    switchPagination (page) {
      this.getLearnList({ page: page, pageSize: this.pageSize })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.grid-wrap {
  background-color: transparent;
}

.learn-list-wrap {
  height: 100%;
  background-color: $--exercise-background-color;
}

</style>
