package hcmute.edu.vn.fefinalproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.fefinalproject.R;
import hcmute.edu.vn.fefinalproject.Model.Subject;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjects;

    private SubjectAdapter.OnSubjectClickListener listener;

    public interface OnSubjectClickListener {
        void onSubjectClick(Subject subject);
    }

    public SubjectAdapter(List<Subject> subjects, OnSubjectClickListener listener) {
        this.subjects = subjects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.subjectName.setText(subject.getName());
        holder.teacherName.setText("Giáo viên: " + subject.getTeacher());
        holder.schedule.setText("Thời gian: " + subject.getSchedule());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSubjectClick(subject);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView teacherName;
        TextView schedule;

        SubjectViewHolder(View view) {
            super(view);
            subjectName = view.findViewById(R.id.subject_name);
            teacherName = view.findViewById(R.id.teacher_name);
            schedule = view.findViewById(R.id.schedule);
        }
    }
} 