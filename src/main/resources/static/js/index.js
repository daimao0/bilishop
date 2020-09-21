

var app = new Vue({
    el: '#app',
    data:{
        mainCities: '',
        moreCities: '',
        tickets:'',
        pageNums:''

    },
    methods:{
        listMainCities:function(){
            axios.get("http://127.0.0.1:8080/listMainCities").then(res=>{
                this.mainCities = res["data"]["data"]
                console.log(this.mainCities)
            }).catch(ret=>{
                console.log(ret)
            })
        },
        listMoreCities:function () {
            axios.get("http://127.0.0.1:8080/listMoreCities").then(res=>{
                this.moreCities = res["data"]["data"]
                console.log(this.moreCities)
            })
        },
        listTickets:function () {
            axios.get("http://127.0.0.1:8080/ticket/list/1").then(res=>{
                console.log(res)
                this.tickets = res["data"]["data"]["result"]
                this.pageNums = res["data"]["data"]["pageNums"]
            })
        },
        listTicketsByTouch:function (page) {
            axios.get("http://127.0.0.1:8080/ticket/list/"+page).then(res=>{
                this.tickets = res["data"]["data"]["result"]
                this.pageNums = res["data"]["data"]["pageNums"]
            })
        },
    },
    created(){
        this.listMainCities()
        this.listMoreCities()
        this.listTickets()
    }
})

