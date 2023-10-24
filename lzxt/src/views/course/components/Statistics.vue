<template>
  <section class="statistic-dashboard">
    <section-header title="统计信息"/>
    <div class="grid-wrap">
      <div class="grid-cell">
        <div class="cell cell-1">
          <div><label>学科名称：</label><span>{{statistic.subjectName || subjectName}}</span></div>
          <div><label>已答：</label><span>{{statistic.answered}}</span></div>
        </div>
        <div class="cell cell-2">
          <div><label>单元：</label><span>{{statistic.subjectUnitList.join(',')}}</span></div>
          <div><label>未答：</label><span>{{statistic.noAnswer}}</span></div>
        </div>
        <div class="cell cell-3">
          <div><label>开始时间：</label><span>{{createTime}}</span></div>
          <div class="text-success"><label>正确：</label><span>{{statistic.trueNum}}</span></div>
        </div>
        <div class="cell cell-4">
          <div><label>题目总数：</label><span>{{statistic.total}}</span></div>
          <div class="text-danger"><label>错误：</label><span>{{statistic.wrongNum}}</span></div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'statistic-dashboard',
  props: {
    statistic: {
      type: Object,
      required: true
    },
    subjectName: String
  },
  computed: {
    createTime () {
      if (this.statistic.createTime) {
        return this.statistic.createTime
      }
      return this.$date.format(new Date())
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.grid-wrap {
  background-color: $--color-white;
  padding: 20px;
}

.cell {
  display: inline-block;
  line-height: 30px;
  vertical-align: top;

  &-1 {
    width: 25%;
  }
  &-2 {
    width: 25%;
  }
  &-3 {
    width: 30%;
  }
  &-4 {
    width: 15%;
  }
}

.text-success {
  color: $--color-success;
}
.text-danger {
  color: $--color-danger;
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 800px ) {
  .cell {
    width: 50%;
  }
}

@media screen and (max-width: 560px ) {
  .cell-1,.cell-3 { width: 70%;}
  .cell-2, .cell-4{ width: 30%;}
}

@media screen and (max-width: 375px ) {
  .cell {
    width: 100%;
  }
}
</style>
