let vue = new Vue({
    el: '#files-app',
    data: {
        textLimit: 50,
        addedFiles: [],
        files: []
    },
    methods: {
        deleteFile: function (file) {
            function postConfirmAction() {

                vue.$http.post('/files/delete/' + file.id).then(
                    function (response) {
                        let index = vue.files.indexOf(file);
                        vue.files.splice(index, 1);
                    },
                    function (error) {}
                );
            }

            this.confirmDelete(postConfirmAction, file.name);
        },
        deleteAddedFile: function (file) {
            let index = vue.addedFiles.indexOf(file);
            vue.addedFiles.splice(index, 1);
        },
        confirmDelete: function (onConfirm, filename) {
            $.confirm({
                columnClass: 'medium',
                title: '',
                content: '<span class="confirm-default-text" >Do you want to delete file <span class="confirm-filename-text" >' + filename + '</span> ?</span>',
                buttons: {
                    confirm: onConfirm,
                    cancel: function () {}
                }
            });
        },
        applyTextFilter: function (string) {
            if (string.length > this.textLimit) {
                string = string.substring(0, this.textLimit) + '...';
            }

            return string;
        },
        downloadLink: function (fileId) {
            return '/files/download/' + fileId;
        },
        onFileChange: function (e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            Array.prototype.forEach.call(files, function (file) {
                vue.addedFiles.push(file);
            });

            console.log('aa');
            e.value = '';
        },
        saveChanges: function () {
            if (this.addedFiles.length === 0) {
                $('#close-modal-btn').click();
                return;
            }

            const data = new FormData();

            Array.prototype.forEach.call(this.addedFiles, function (file) {
                data.append('files', file);
            });

            this.$http.post('/files/upload', data, {
                emulateJSON: true
            }).then(
                function (response) {
                    vue.addedFiles = [];
                    $('#close-modal-btn').click();
                    vue.updateFiles();
                },
                function (error) {}
            );
        },
        updateFiles: function () {
            this.$http.post('/files/getAll').then(
                function (response) {
                    this.files = response.body;
                },
                function (error) {}
            )
        }
    },
    mounted: function () {
        this.updateFiles();
    }
});