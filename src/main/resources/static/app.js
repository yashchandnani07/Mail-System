const API_URL = '/api/admin';

document.addEventListener('DOMContentLoaded', () => {
    loadStudents();
    loadHistory();

    document.getElementById('add-student-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const submitBtn = e.target.querySelector('button');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<span class="icon">⌛</span> Adding...';
        submitBtn.disabled = true;

        const student = {
            name: document.getElementById('student-name').value,
            email: document.getElementById('student-email').value,
            department: document.getElementById('student-dept').value,
            semester: document.getElementById('student-sem').value
        };

        try {
            await fetch(`${API_URL}/students`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(student)
            });
            e.target.reset();
            loadStudents();
        } catch (error) {
            console.error('Error adding student:', error);
            alert('Failed to add student.');
        } finally {
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        }
    });

    document.getElementById('send-circular-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const submitBtn = e.target.querySelector('button');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<span class="icon">✈</span> Sending...';
        submitBtn.disabled = true;

        // Use FormData to handle file uploads
        const formData = new FormData();
        formData.append('subject', document.getElementById('circular-subject').value);
        formData.append('message', document.getElementById('circular-message').value);
        formData.append('department', document.getElementById('circular-dept').value);
        formData.append('semester', document.getElementById('circular-sem').value);

        const fileInput = document.getElementById('circular-file');
        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]);
        }

        try {
            // Note: Content-Type header is NOT set manually for FormData
            const response = await fetch(`${API_URL}/send-circular`, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                alert('Circular sent successfully!');
                e.target.reset();
                loadHistory();
            } else {
                alert('Failed to send circular.');
            }
        } catch (error) {
            console.error('Error sending circular:', error);
            alert('Error sending circular.');
        } finally {
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        }
    });

    /**
     * Event listener for the "Clear History" button.
     * Prompts for confirmation before deleting all history.
     */
    document.getElementById('clear-history-btn').addEventListener('click', async () => {
        if (confirm('Are you sure you want to delete ALL circular history? This cannot be undone.')) {
            try {
                await fetch(`${API_URL}/history`, { method: 'DELETE' });
                loadHistory(); // Reload the list (should be empty)
                alert('History cleared successfully.');
            } catch (error) {
                console.error('Error clearing history:', error);
                alert('Failed to clear history.');
            }
        }
    });
});

async function loadStudents() {
    try {
        const response = await fetch(`${API_URL}/students`);
        const students = await response.json();
        const list = document.getElementById('student-list');
        list.innerHTML = '';

        if (students.length === 0) {
            list.innerHTML = '<li style="text-align:center; color: #999;">No students registered yet.</li>';
            return;
        }

        students.forEach(s => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div class="student-info">
                    <span class="student-name">${s.name}</span>
                    <span class="student-details">${s.department} • Sem ${s.semester} • ${s.email}</span>
                </div>
                <button class="delete-btn" onclick="deleteStudent('${s.id}')">Remove</button>
            `;
            list.appendChild(li);
        });
    } catch (error) {
        console.error('Error loading students:', error);
    }
}

async function deleteStudent(id) {
    if (confirm('Are you sure you want to remove this student?')) {
        try {
            await fetch(`${API_URL}/students/${id}`, { method: 'DELETE' });
            loadStudents();
        } catch (error) {
            console.error('Error deleting student:', error);
        }
    }
}

async function loadHistory() {
    try {
        const response = await fetch(`${API_URL}/history`);
        const history = await response.json();
        const list = document.getElementById('circular-history');
        list.innerHTML = '';

        if (history.length === 0) {
            list.innerHTML = '<li style="text-align:center; color: #999;">No circulars sent yet.</li>';
            return;
        }

        // Sort by date desc
        history.sort((a, b) => new Date(b.sentDate) - new Date(a.sentDate));

        history.forEach(h => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div class="history-item" style="width: 100%">
                    <div class="history-subject">${h.subject}</div>
                    <div class="history-meta">
                        <span>${new Date(h.sentDate).toLocaleString()}</span>
                        <span>Sent to: <strong>${h.recipientCount}</strong> students</span>
                    </div>
                </div>
            `;
            list.appendChild(li);
        });
    } catch (error) {
        console.error('Error loading history:', error);
    }
}
