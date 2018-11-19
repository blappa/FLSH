function handleSubmit(xhr, status, args, dialog) {
    var jqDialog = jQuery('#' + dialog.id);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        dialog.hide();
    }
}

function cancel() {
    clearInterval(this.progressInterval);
    pbClient.setValue(0);
}  

function start() {
    PF('startButton1').disable();
 
    window['progress'] = setInterval(function() {
        var pbClient = PF('pbClient'),
        oldValue = pbClient.getValue(),
        newValue = oldValue + 10;
 
        pbClient.setValue(pbClient.getValue() + 10);
 
        if(newValue === 100) {
            clearInterval(window['progress']);
        }
 
 
    }, 1000);
}