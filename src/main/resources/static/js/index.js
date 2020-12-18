

var main = {
    init : function () {
        var _this = this;
        $('#place-find').on('click',function () {
            _this.find();
        });
    },
    find : function () {
        var keyword = $('#keyword').val();
        var option = $('#select1 option:selected').val();
        var search = keyword + option;
        $.ajax({
            type:'GET',
            url:'/api/v1/place/'+search,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function(res){
            //alert(JSON.stringify(res));
            window.location.href = '/place/result/'+search;
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

main.init();