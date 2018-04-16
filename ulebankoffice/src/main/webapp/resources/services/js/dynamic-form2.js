var movesContainer = $('#moves-container');
var movesCounter = 1;

/* Obteniendo las variables internacionalizadas del código html*/
var mDescription = strings['movimiento.descripcion'];
var mDate = strings['movimiento.fecha'];
var mAmount = strings['movimiento.importe'];
var mOperation = strings['movimiento.operacion'];
var mIngreso = strings['movimiento.ingreso'];
var mDisposicion = strings['movimiento.disposicion'];

window.onload = function() {

    if ($.cookie("movimientos")) {
        restaurarForm();
        replicarImportes();
        replicarFechas();
    }

    if ($.cookie("resultados") === "1") {
        $('#modalResultados').modal('show');
        $.cookie("resultados", null);
    }

    $("#limiteCredito").on("change paste keyup", function() {
        replicarImportes();
    });

    $("#fechaApertura").on("change paste keyup", function() {
        replicarFechas();
    });

    $("#comisionApertura").on("change paste keyup", function() {
        replicarImportes();
    });

    $("#corretaje").on("change paste keyup", function() {
        replicarImportes();
    });

    $("#add-moves").click(function() {

        var row = $('<div class="row" style="border: 1px solid red; border-radius: 5px;"/>');
        var inputs = $('<div class="section-field col-md-4">' +
            '<label for="movimientos' + movesCounter + '.concepto">' + mDescription + '</label>' +
            '<div class="field-widget"><i class="fa fa-pencil" ></i><input name="movimientos[' + movesCounter + '].concepto" id="movimientos' + movesCounter + '.concepto" type="text" required="required"/>' +
            '</div>' +
            '<form:errors path="movimientos[0].concepto"/>' +
            '</div>' +

            '<div class="section-field col-md-3">' +
            '<label for="movimientos' + movesCounter + '.importe">' + mAmount + '</label>' +
            '<div class="field-widget">' +
            '<i class="fa fa-eur" ></i>' +
            '<input name="movimientos[' + movesCounter + '].importe" id="movimientos' + movesCounter + '.importe" type="text" value="0.0" required="required"/>' +
            '</div>' +
            '<form:errors path="movimientos[0].importe"/>' +
            '</div>' +

            '<div class="section-field col-md-3">' +
            '<label for="movimientos' + movesCounter + '.fechaValor">' + mDate + '</label>' +
            '<div class="field-widget">' +
            '<i class="fa fa-calendar"></i>' +
            '<input name="movimientos[' + movesCounter + '].fechaValor" id="movimientos' + movesCounter + '.fechaValor" type="date" value="0" required="required"/>' +
            '</div>' +
            '<form:errors path="movimientos[0].fechaValor"/>' +
            '</div>' +

            '<div class="section-field col-md-2">' +
            '<label for="movimientos[' + movesCounter + '].operacion">' + mOperation + '</label>' +
            '<div class="field-widget">' +
            '<select name="movimientos[' + movesCounter + '].operacion" class="selected">' +
            '<option value="D" label="' + mDisposicion + '" />' +
            '<option value="I" label="' + mIngreso + '" />' +
            '<select> ' +
            '</div>' +
            '<form:errors path="movimientos[0].operacion"/>' +
            '</div>' +
            '</div>');


        row.append(inputs);

        row.appendTo(movesContainer);

        movesCounter++;
    });

    $("#remove-moves").click(function() {
        if (movesCounter > 1) {
            $('#moves-container .row').last().remove();
            $('#moves-container br').last().remove();

            movesCounter--;
        }
    });
}

function replicarImportes() {
    $("#importe-cero").val($("#limiteCredito").val() * ($("#comisionApertura").val() / 100.0));
    $("#importe-uno").val($("#limiteCredito").val() * ($("#corretaje").val() / 1000.0));
}

function replicarFechas() {
    $("#fecha-cero").val($("#fechaApertura").val());
    $("#fecha-uno").val($("#fechaApertura").val());
}

function restaurarForm() {
    $("#fechaApertura").val($.cookie("fechaApertura"));
    $("#fechaVencimiento").val($.cookie("fechaVencimiento"));

    var movimientos = $.parseJSON($.cookie("movimientos"));

    movesContainer.empty();
    movesCounter = 0;

    for (var i = 0; i < movimientos.length; i++) {
        addMovimiento(movimientos[i].concepto, movimientos[i].importe, movimientos[i].fechaValor, i);
    }

    var date = new Date();
    date.setTime(date.getTime() + (1 * 1000));

    $.cookie("movimientos", null, {
        expires: date
    });
    $.cookie("fechaApertura", null, {
        expires: date
    });
    $.cookie("fechaVencimiento", null, {
        expires: date
    });
}
