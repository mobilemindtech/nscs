
//=native
import { createApp } from 'nativescript-vue';
//=

//=web
import { createApp } from 'vue/dist/vue.esm-bundler'
//import { createApp } from 'vue';
//=

import HomeView from './components/HomeView.vue';


//=native
createApp(HomeView).start();
//=

//=web
createApp(HomeView).mount('#app')
//=


