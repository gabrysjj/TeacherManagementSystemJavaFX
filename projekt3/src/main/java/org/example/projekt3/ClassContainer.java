package org.example.projekt3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class ClassContainer {
    public Map<String, ClassTeacher> classGroups;

    public ClassContainer()
    {
        this.classGroups = new HashMap<>();
    }
    public void addClass(String groupName, int maxTeachers)
    {
        if (classGroups.containsKey(groupName))
        {
            System.out.println("Grupa o nazwie " + groupName + " już istnieje.");
        }
        else
        {
            classGroups.put(groupName, new ClassTeacher(groupName, maxTeachers));
            System.out.println("Dodano grupę: " + groupName);
        }
    }
    public void removeClass(String groupName)
    {
        if (classGroups.remove(groupName) != null)
        {
            System.out.println("Usunięto grupę: " + groupName);
        }
        else
        {
            System.out.println("Grupa o nazwie " + groupName + " nie istnieje.");
        }
    }
    public List<String> findEmpty()
    {
        List<String> emptyGroups = new ArrayList<>();
        for (Map.Entry<String, ClassTeacher> entry : classGroups.entrySet())
        {
            if (entry.getValue().getTeachers().isEmpty())
            {
                emptyGroups.add(entry.getKey());
            }
        }
        return emptyGroups;
    }
    public void summary()
    {
        System.out.println("Podsumowanie grup nauczycielskich:");
        for (Map.Entry<String, ClassTeacher> entry : classGroups.entrySet())
        {
            String groupName = entry.getKey();
            ClassTeacher classTeacher = entry.getValue();
            double filledPercentage = 100.0 * classTeacher.getTeachers().size() / classTeacher.getMaxTeachers();
            System.out.printf("Grupa: %s, Zapełnienie: %.2f%%\n", groupName, filledPercentage);
        }
    }
}
