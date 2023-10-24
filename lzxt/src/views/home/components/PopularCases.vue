<template>
  <section class="case-list-section">
    <section-header title="案例分享" more="/cases"></section-header>
    <ul class="case-list">
      <template v-for="(item, index) in firstRows">
        <li class="space" v-if="index !== 0 " :key="`row_space_1_${index}`"></li>
        <li class="case-list-item" :key="`row_item_1_${index}`">
          <router-link :to="`/cases/${item.id}`">
            <img :src="item.imageUrl" :alt="item.title" />
          </router-link>
        </li>
      </template>
    </ul>
    <ul class="case-list" v-if="secondRows.length > 0">
      <template v-for="(item, index) in secondRows">
        <li class="space" v-if="index !== 0 " :key="`row_space_2_${index}`"></li>
        <li class="case-list-item" :key="`row_item_2_${index}`">
          <router-link :to="`/cases/${item.id}`">
            <img :src="item.imageUrl" :alt="item.title" />
          </router-link>
        </li>
      </template>
    </ul>
  </section>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'popular-cases',
  computed: {
    ...mapState('case', {
      caseList: state => state.caseList.list
    }),
    firstRows () {
      return this.caseList.slice(0, 4)
    },
    secondRows () {
      if (this.caseList.length > 4) {
        return this.caseList.slice(4)
      } else {
        return []
      }
    }
  },
  mounted () {
    this.getCaseList({ page: 1, pageSize: 8 })
  },
  methods: {
    ...mapActions('case', ['getCaseList'])
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.case-list-section {
  margin: 50px 0;

  .case-list {
    margin-top: 20px;
    display: flex;
    flex-wrap: wrap;
    flex-grow: 10px;
    justify-content: space-between;

    &-item {
      display: inline-block\9;
      vertical-align: top\9;
      width: 23.5%\9;
      flex: 11;

      a {display: block;}

      img {
        display: block;
        max-width: 100%;
        margin: 0 auto;
      }
    }

    .space {
      display: inline-block\9;
      vertical-align: top\9;
      width: 2%\9;
      flex: 1;
    }
  }
}
</style>

<style lang="scss" scoped>
  @import '~@/styles/vars.scss';

  @media screen and (max-width: 480px) {
    .case-list-item {
      width: 48%;
    }
  }

</style>
