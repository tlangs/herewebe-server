/**
 * Created by trevyn on 4/19/14.
 */

function clearNavButtons() {
    $('#nav-buttons').children().each(function() {
        $(this).removeClass('active');
    });
}