<template>
  <section class="wrong-notebook">
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>错题本</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="condition-wrap">
      <el-form inline model="conditions" label-width="60px" @submit.native.prevent="handleConditionChange">
          <el-form-item label="学科:">
            <subject-select v-model="conditions.subjectName" />
          </el-form-item>
          <el-form-item label="年级:">
            <grade-select v-model="conditions.subjectGrade" />
          </el-form-item>
          <el-form-item label="学期:">
            <term-select v-model="conditions.subjectTerm" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit">查询</el-button>
          </el-form-item>
      </el-form>
    </div>
    <empty-list v-if="isEmpty">
      <span>少年，我为你的聪明感到无比的惊讶，竟然没有发现你做出过任何题目！！！</span>
    </empty-list>
    <template v-else>
      <div class="grid-wrap">
        <practice-question v-for="(item, index) in list" view :item="item.topic" :user-answer="item.errorAnswer" :index="(conditions.page - 1) * conditions.pageSize + index + 1" :key="index">
          <answer-dashboard slot-scope="scope"
            :user-answer="scope.userAnswerText"
            :right-answer="scope.rightAnswerText"
            :judge="false"
            :status="1"
            align
            :wrong-time="item.errorTime"
            :wrong-counts="item.errorCount"
            :topic-type="scope.topicType">
          </answer-dashboard>
        </practice-question>
      </div>
      <div class="footer-wrap" v-if="showFooter">
        <el-pagination
          layout="total, prev, pager, next, jumper"
          hide-on-single-page
          :current-page.sync="conditions.page"
          :page-size="conditions.pageSize"
          :total="total"
          @current-change="switchPagination">
        </el-pagination>
      </div>
    </template>
  </section>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex'

export default {
  name: 'wrong-notebbok',
  data () {
    return {
      conditions: {
        page: 1,
        pageSize: 10,
        subjectName: '全部',
        subjectGrade: '全部',
        subjectTerm: '全部'
      }
    }
  },
  computed: {
    ...mapGetters('field', ['conditionGrades', 'conditionSubjects', 'conditionTerms']),
    ...mapState('exams', {
      list: state => state.wrongTopicList.list,
      total: state => state.wrongTopicList.total
    }),
    isEmpty () {
      return this.total === 0
    },
    showFooter () {
      return this.total > this.conditions.pageSize
    }
  },
  mounted () {
    this.getWrongTopicList({ ...this.conditions })
  },
  methods: {
    ...mapActions('exams', { getWrongTopicList: 'getUserWrongTopicList' }),
    switchPagination (page) {
      this.getWrongTopicList({ ...this.conditions, page }).then(() => {
        this.$scrollTo(true)
      })
    },
    handleConditionChange () {
      this.getWrongTopicList({ ...this.conditions, page: 1 })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.condition-wrap {
  padding: 15px 40px 0;
  border-top: $--border-base;

  .el-form-item {
    margin-bottom: 15px ;
  }

  .el-select {
    width: 120px;
  }

  .el-button {
    min-width: 80px;
  }
}

.answer-dashboard {
  margin: 30px 0;
}
</style>

<style lang="scss">
@media screen and (max-width: 500px) {
  .wrong-notebook {
    .el-form--inline .el-form-item {
      display: block;

      .el-form-item__content {
        width: calc(100% - 60px);
      }

      .el-select {
        width: 100%;
      }
    }
  }
}
</style>
