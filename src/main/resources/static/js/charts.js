fetch('/api/job-stats')
    .then(response => response.json())
    .then(data => {
        new Chart(document.getElementById('jobTitleChart'), {
            type: 'bar',
            data: {
                labels: [...data.jobTitles],
                datasets: [{
                    label: 'Number of Jobs',
                    data: [...data.jobCounts],
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
        new Chart(document.getElementById('locationChart'), {
            type: 'pie',
            data: {
                labels: [...data.locations],
                datasets: [{
                    data: [...data.locationCounts],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(255, 206, 86, 0.5)',
                        'rgba(75, 192, 192, 0.5)'
                    ]
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    })
    .catch(error => console.error('Error:', error));