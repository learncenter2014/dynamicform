//called the Datepicker component of jQuery UI 
function displayCalendarName(n,format,buttonObj,displayTime,timeInput)
{
    jQuery("#f_" + _no).find("input").datepicker({
        showOn : 'button',
        buttonImage : '../img/rbl/_cal.gif',
        buttonImageOnly : true,
        dateFormat : rblEltArray[_no].e_mask.replace(/yy/g, "y"),
        showMonthAfterYear : false
    });
}

