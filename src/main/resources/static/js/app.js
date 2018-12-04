window.onload = function (ev) {
    new Vue({
        el: "#app",
        data: {
            feedback: '',
            serviceInput: '',
            createProgressDoesItContinue: false,
            selectedServices: [],
            version: '',
            versions: [],
            defaultServices: []
        },
        mounted: function(){
            this.$http.get("/api/v1/docker-compose")
                .then(function(response){
                    this.versions = response.data.versions;
                    this.defaultServices = response.data.services;
                }, function (error) {
                    console.error("An error occurred while fetching versions! Error: ", error);
                }).bind(this);
        },
        methods: {
            deleteService: function (service) {
                this.selectedServices.splice(this.selectedServices.indexOf(service), 1);
            },
            showNotification: function (title, message, type, position) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type,
                    position: position
                });
            },
            download: function (response) {
                var headers = response.headers;
                var blob = new Blob([response.data], {type: headers['content-type']});
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = "docker-compose.yml";
                link.click();
            },
            createDockerComposeFile: function () {
                if(this.checkVersionIsSelectedAndLeastOneServiceSelected()){
                    this.createProgressDoesItContinue = true;
                    this.$http.post('/api/v1/docker-compose', { version: this.version, services: this.selectedServices })
                        .then(function (response) {
                            this.download(response);
                            this.showNotification('Success', 'docker-compose.yml file successfully created :)', 'success', 'top-right');

                            this.selectedServices = [];
                            this.version = '';
                            this.createProgressDoesItContinue = false;

                            gtag('event', 'docker-compose-generate', { version: this.version, services: this.selectedServices });
                        }, function (error) {
                            console.error(error);
                            this.createProgressDoesItContinue = false;
                        }).bind(this);
                }
            },
            sendFeedback: function(){
                if(this.feedback !== ''){
                    this.$http.post('/api/v1/feedback', this.feedback)
                        .then(function (response){
                            this.feedback = '';
                            this.showNotification('Success', 'Feedback successfully send, thank you :)', 'success', 'top-right');
                        }, function (error) {
                            console.error(error);
                            this.feedback = '';
                        }).bind(this);
                }else{
                    this.showWarning("Please type somethings :)", 'info');
                }
            },
            querySearch: function (queryString, cb) {
                var results = queryString ? this.defaultServices.filter(this.createFilter(queryString)) : this.defaultServices;
                // call callback function to return suggestions
                cb(results);
            },
            createFilter: function (queryString) {
                return (service) =>{
                    return (service.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                }
            },
            handleSelect: function (service) {
                this.serviceInput = '';
                if (!this.selectedServices.includes(service.value)){
                    this.selectedServices.push(service.value);
                }
            },
            showWarning: function (message, type) {
                this.$message({
                    message: message,
                    type: type
                });
            },
            checkVersionIsSelectedAndLeastOneServiceSelected: function () {
                if(this.version === ''){
                    this.showWarning('Please select docker compose version', 'warning');
                    return false;
                }

                if(this.selectedServices.length === 0){
                    this.showWarning('Please select least one service', 'warning');
                    return false;
                }

                return true;
            }
        }
    });
};