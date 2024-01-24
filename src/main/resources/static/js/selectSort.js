$(document).ready(function () {
    var originalRows = $('#commentTable tbody tr').clone();

    $('#sort').change(function () {
        var selectedSort = $(this).val();

        if (selectedSort === 'basic') {
            resetTable();
        } else {
            sortTable(selectedSort);
        }
    });

    function resetTable() {
        $('#commentTable tbody').empty().append(originalRows.clone());
    }

    function sortTable(selectedSort) {
        var rows = $('#commentTable tbody tr').get();

        rows.sort(function (a, b) {
            var keyA = $(a).children('td').eq(getColumnIndex(selectedSort)).text();
            var keyB = $(b).children('td').eq(getColumnIndex(selectedSort)).text();

            if (selectedSort === 'time') {
                return new Date(keyB) - new Date(keyA);
            } else if (selectedSort === 'likeCount') {
                return parseInt(keyB, 10) - parseInt(keyA, 10);
            } else {
                return 0;
            }
        });

        $.each(rows, function (index, row) {
            $('#commentTable tbody').append(row);
        });
    }

    function getColumnIndex(selectedSort) {
        var columnIndex = 1;

        if (selectedSort === 'time') {
            columnIndex = 2;
        } else if (selectedSort === 'likeCount') {
            columnIndex = 3;
        }

        return columnIndex;
    }


});
