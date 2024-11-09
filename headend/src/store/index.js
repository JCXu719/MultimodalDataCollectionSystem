import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        accessToken: null,
        score_array: []  // 新增的变量  
    }, getters: {
        isAuthenticated: state => state.accessToken !== null,
        scoreArray: state => state.score_array !== null  // 新增的 getter 
    }, mutations: {
        SET_ACCESS_TOKEN(state, accessToken) {
            state.accessToken = accessToken;
        }, CLEAR_ACCESS_TOKEN(state) {
            state.accessToken = null;
        },
        SET_SCORE_ARRAY(state, scoreArray) {  // 新增的 mutation  
            state.score_array = scoreArray;  
        },  
    },
});
