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
        created: function(){
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
            createDockerComposeFile: function () {
                if(this.checkVersionIsSelectedAndLeastOneServiceSelected()){
                    this.createProgressDoesItContinue = true;
                    this.$http.post('/api/v1/docker-compose', { version: this.version, services: this.selectedServices })
                        .then(function (response) {
                            var headers = response.headers;
                            var blob = new Blob([response.data],{type:headers['content-type']});
                            var link = document.createElement('a');
                            link.href = window.URL.createObjectURL(blob);
                            link.download = "docker-compose.yml";
                            link.click();

                            this.selectedServices = [];
                            this.version = '';
                            this.$notify({
                                title: 'Success',
                                message: 'docker-compose.yml file successfully created :)',
                                type: 'success',
                                position: 'bottom-right'
                            });
                            this.createProgressDoesItContinue = false;

                            gtag('event', 'docker-compose-generate', { version: this.version, services: this.selectedServices });
                        }, function (error) {
                            console.error(error);
                            this.createProgressDoesItContinue = false;
                        }).bind(this);
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
            checkVersionIsSelectedAndLeastOneServiceSelected: function () {
                if(this.version === ''){
                    this.$message({
                        message: 'Please select docker compose version',
                        type: 'warning'
                    });

                    return false;
                }

                if(this.selectedServices.length === 0){
                    this.$message({
                        message: 'Please select least one service',
                        type: 'warning'
                    });

                    return false;
                }

                return true;
            }
        }
    });
};