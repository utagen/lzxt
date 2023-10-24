<template>
  <section class="answer-status-sheet">
    <section-header title="答题卡"/>
    <div class="grid-wrap">
      <ul class="grid-cell">
          <li v-for="(item, index) in list"
            :tabindex="item.pStatus | tabindex"
            :class="item.pStatus | statusClasses"
            :key="item.topicId"
            @click.stop="handleCellClicked(item, index + 1)">
            {{index + 1}}
          </li>
      </ul>
    </div>
  </section>
</template>

<script>
export default {
  name: 'answer-status-sheet',
  props: {
    list: {
      type: Array,
      required: true
    }
  },
  filters: {
    statusClasses (status) {
      return ['answer-status', {
        'answer-status__correct': status === 2,
        'answer-status__incorrect': status === 1,
        'answer-status__uncompleted': status === 0
      }]
    },
    tabindex (status) {
      return status !== 0 ? '0' : '-1'
    }
  },
  methods: {
    handleCellClicked (item, index) {
      if (item.pStatus !== 0) {
        this.$emit('on-selected', index)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.grid-wrap {
  background-color: $--color-white;
  padding: 10px;
}

.answer-status {
  display: inline-block;
  text-align: center;
  line-height: 30px;
  font-size: $--font-size-extra-small;
  border-radius: $--border-radius-small;
  width: 30px;
  height: 30px;
  margin: 5px;

  &__correct {
    cursor: pointer;
    color: $--color-white;
    background-color: $--color-success;
  }

  &__incorrect {
    cursor: pointer;
    color: $--color-white;
    background-color: $--color-danger;
  }

  &__uncompleted {
    border: $--border-base;
  }
}
</style>
