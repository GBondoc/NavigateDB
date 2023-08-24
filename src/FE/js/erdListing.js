const erdGrid = document.querySelector('.erd--grid');

const erdNames = ['Database 1', 'Database 2', 'Database 3', 'Database 4', 'Database 5', 'Database 6'];

erdNames.forEach(erdName => {
    const erdSquare = document.createElement('div');
    erdSquare.classList.add('erd-square');
    erdSquare.textContent = erdName;
    erdGrid.appendChild(erdSquare);
});
