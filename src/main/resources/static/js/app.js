window.onload = function (ev) {
    new Vue({
        el: "#app",
        data: {
            serviceInput: '',
            selectedServices: []
        },
        methods: {
            addService: function () {
                this.selectedServices.push(this.serviceInput);
                this.serviceInput = '';
            },
            deleteService: function (service) {
                console.log(service);
            },
            createDockerComposeFile: function () {
                console.log(this.selectedServices);
            }
        }
    });
};