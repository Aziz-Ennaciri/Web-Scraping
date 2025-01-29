document.addEventListener('DOMContentLoaded', function() {
    const chartsView = document.getElementById("charts-view");
    const tableView = document.getElementById("table-view");
    const table1 = document.getElementById("table");
    const charts1 = document.getElementById("charts");

    function setActiveLink(activeLink) {
        document.querySelectorAll(".nav-link").forEach(link => link.classList.remove("active"));
        activeLink.classList.add("active");
    }

    tableView.addEventListener("click", (e) => {
        e.preventDefault();
        console.log("Table clicked");
        table1.style.display = "block";
        charts1.style.display = "none";
        setActiveLink(tableView);
    });

    chartsView.addEventListener("click", (e) => {
        e.preventDefault();
        console.log("Charts clicked");
        table1.style.display = "none";
        charts1.style.display = "block";
        setActiveLink(chartsView);
    });
});
